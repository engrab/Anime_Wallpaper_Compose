package com.example.anime.domain.usecases

import android.app.Application
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.anime.data.model.Category
import com.example.anime.data.model.Wallpaper
import com.example.anime.domain.repository.WallpaperRepository
import com.example.anime.domain.source.CategoryPagingSource
import com.example.anime.domain.source.WallpaperPagingSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class WallpaperUseCase
@Inject
constructor(
    // remote from unsplash
    val getWallpapers: GetWallpapers,
    val getWallpaper: GetWallpaper,
    val getCollections: GetCollections,
    val getWallpapersByCollection: GetWallpapersByCollection,

    val downloadWallpaper: DownloadWallpaper,
    val setWallpaperUseCases: SetWallpaperUseCases,

    // local database
    val getFavourites: GetFavourites,
    val addFavourite: AddFavourite,
    val removeFavourite: RemoveFavourite
)

class SetWallpaperUseCases
@Inject
constructor(private val context: Application) {
    private val wpm: WallpaperManager =
        context.getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager

    suspend operator fun invoke(wallpaper: Wallpaper, flag: Int): Result<Boolean> {
        return kotlin.runCatching {
            val bitmap = context.getBitmap(wallpaper.wallpaperUrl)
            invoke(bitmap, flag)
        }.getOrElse {
            Log.e(TAG, "invoke: ${it.message}")
            Result.failure(it)
        }
    }

    suspend operator fun invoke(bitmap: Bitmap, flag: Int): Result<Boolean> {
        return withContext(Dispatchers.Default) {
            kotlin.runCatching {
                val res = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    wpm.setBitmap(bitmap, null, true, flag)
                } else {
                    TODO("VERSION.SDK_INT < N")
                }
                Log.d(TAG, "invoke: $res")
                if (res == 0) {
                    throw Throwable("Image processing failed!")
                }
                Result.success(true)
            }.getOrElse {
                Log.e(TAG, "invoke: ${it.message}")
                Result.failure(it)
            }
        }
    }

    companion object {
        const val TAG = "SetWallpaperUseCases"
    }
}


class GetWallpaper
@Inject
constructor(
    private val repository: WallpaperRepository
) {
    suspend operator fun invoke(id: String): Wallpaper {
        return repository.getWallpaper(id)
    }
}

class GetWallpapers
@Inject
constructor(
    private val repository: WallpaperRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Wallpaper>> {
        return Pager(PagingConfig(pageSize = 20)) {
            WallpaperPagingSource(repository, query, false)
        }.flow
    }
}

class GetWallpapersByCollection
@Inject
constructor(
    private val repository: WallpaperRepository
) {
    operator fun invoke(collectionId: String): Flow<PagingData<Wallpaper>> {
        return Pager(PagingConfig(pageSize = 20)) {
            WallpaperPagingSource(repository, collectionId, true)
        }.flow
    }
}


class GetFavourites
@Inject
constructor(
    private val repository: WallpaperRepository
) {
    operator fun invoke(): Flow<List<Wallpaper>> = repository.getFavourites()
}

class AddFavourite
@Inject
constructor(
    private val repository: WallpaperRepository
) {
    suspend operator fun invoke(wallpaper: Wallpaper) = repository.addFavourite(wallpaper)
}

class RemoveFavourite
@Inject
constructor(
    private val repository: WallpaperRepository
) {
    suspend operator fun invoke(id: String) = repository.removeFavourite(id)
}


class DownloadWallpaper
@Inject
constructor(
    private val repository: WallpaperRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(wallpaper: Wallpaper): Result<Uri?> {
        return try {
            val bitmap = context.getBitmap(wallpaper.wallpaperUrl)
            val uri = repository.downloadWallpaper(bitmap, "IMG${wallpaper.id}.jpg")
            Result.success(uri)
        } catch (e: Exception) {
            Log.e(TAG, "invoke: ${e.message}")
            Result.failure(e)
        }
    }

    companion object {
        const val TAG = "DownloadWallpaper"
    }
}

suspend fun Context.getBitmap(url: String): Bitmap {
    return withContext(Dispatchers.IO) {
        val loader = ImageLoader(this@getBitmap)
        val request = ImageRequest.Builder(this@getBitmap)
            .data(url)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()
        val res = loader.execute(request)
        when (res) {
            is SuccessResult -> {
                val result = res.drawable
                (result as BitmapDrawable).bitmap
            }

            else -> {
                throw (res as ErrorResult).throwable
            }
        }
    }
}


class GetCollections
@Inject
constructor(
    private val repository: WallpaperRepository
) {
    operator fun invoke(): Flow<PagingData<Category>> {
        return Pager(config = PagingConfig(pageSize = 20)) {
            CategoryPagingSource(repository)
        }.flow
    }

    companion object {
        private const val TAG = "GetCollections"
    }
}
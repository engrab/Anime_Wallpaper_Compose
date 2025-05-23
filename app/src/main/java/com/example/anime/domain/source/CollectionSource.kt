package com.example.anime.domain.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.anime.data.model.Collection
import com.example.anime.domain.repository.WallpaperRepository

class CollectionSource(
    private val repository: WallpaperRepository
) : PagingSource<Int, Collection>() {

    companion object {
        const val TAG = "CollectionSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collection> {
        return kotlin.runCatching {
            val nextPage = params.key ?: 1
            val collections = repository.getCollections(nextPage)
            LoadResult.Page(
                data = collections,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage.plus(1)
            )
        }.getOrElse {
            Log.e(TAG, "load: ${it.message}")
            LoadResult.Error(it)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Collection>): Int? {
        return null
    }
}

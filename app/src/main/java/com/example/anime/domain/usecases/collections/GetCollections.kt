package com.example.anime.domain.usecases.collections

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.anime.data.model.Collection
import com.example.anime.domain.repository.WallpaperRepository
import com.example.anime.domain.source.CollectionSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollections
@Inject
constructor(
    private val repository: WallpaperRepository
) {
    operator fun invoke(): Flow<PagingData<Collection>> {
        return Pager(config = PagingConfig(pageSize = 20)) {
            CollectionSource(repository)
        }.flow
    }

    companion object {
        private const val TAG = "GetCollections"
    }
}

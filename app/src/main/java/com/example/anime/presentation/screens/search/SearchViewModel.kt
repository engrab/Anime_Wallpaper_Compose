package com.example.anime.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.anime.data.model.Wallpaper
import com.example.anime.domain.usecases.WallpaperUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val wallpaperUseCase: WallpaperUseCase
) : ViewModel() {

    private var searchJob: Job? = null
    val wallpapers = mutableStateOf(flowOf<PagingData<Wallpaper>>())

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            wallpapers.value = wallpaperUseCase.getWallpapers(query)
        }
    }
}

package com.example.paging3compose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.example.paging3compose.data.model.PhotoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WallpaperViewModel @Inject constructor(
    pager: Pager<Int, PhotoEntity>
) : ViewModel() {
    val photoList = pager.flow.cachedIn(viewModelScope)
}

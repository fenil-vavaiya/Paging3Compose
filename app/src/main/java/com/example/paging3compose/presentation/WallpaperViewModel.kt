package com.example.paging3compose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.paging3compose.data.paging.MyPagingSource

class WallpaperViewModel : ViewModel() {
    val pager = Pager(
        PagingConfig(
            pageSize = 20,  // Increase to reduce network calls
            prefetchDistance = 5,  // Load next page 5 items before the end
            initialLoadSize = 40,  // Load more items initially
            enablePlaceholders = false // Disable placeholders for smooth loading
        )
    ) {
        MyPagingSource("people")
    }.flow.cachedIn(viewModelScope)

}

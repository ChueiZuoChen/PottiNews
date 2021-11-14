package com.czchen.pottinews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.czchen.pottinews.repository.NewsRepository
import java.lang.Exception

class NewsViewModelFactory(val repository: NewsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }
}
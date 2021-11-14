package com.czchen.pottinews.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czchen.pottinews.models.Article
import com.czchen.pottinews.models.NewsResponse
import com.czchen.pottinews.repository.NewsRepository
import com.czchen.pottinews.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val repository: NewsRepository) : ViewModel() {

    private val _breakingNews = MutableLiveData<Resource<NewsResponse>>()
    val breakingNews: LiveData<Resource<NewsResponse>> = _breakingNews
    private var breakingNewsPage = 1


    private val _searchNews = MutableLiveData<Resource<NewsResponse>>()
    val searchNews: LiveData<Resource<NewsResponse>> = _searchNews
    private var searchNewsPage = 1

    init {
//        searchNews("covid")
        getBreakingNews("au")
    }

    fun searchNews(searchQuery:String) = viewModelScope.launch {
        _searchNews.postValue(Resource.Loading())
        val response = repository.searchNews(searchQuery,searchNewsPage)
        _searchNews.postValue(handleSearchNewsResponse(response))
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        /**先Loading*/
        _breakingNews.postValue(Resource.Loading())
        val response = repository.getBreakingNews(countryCode, breakingNewsPage)
        /**將Response<NewsResponse> 傳進 handle 包裝成Resource<NewsResponse>*/
        _breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        /**如果成功把NewsResponse用Resource<>包裝起來回傳給 livedata*/
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse> ):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.insertArticle(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

    fun getArticles() = repository.getArticles()
}
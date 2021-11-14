package com.czchen.pottinews.repository

import androidx.lifecycle.LiveData
import com.czchen.pottinews.api.RetrofitInstance
import com.czchen.pottinews.db.ArticleDatabase
import com.czchen.pottinews.models.Article
import com.czchen.pottinews.models.NewsResponse
import com.czchen.pottinews.util.Constants.Companion.API_KEY
import retrofit2.Response

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return RetrofitInstance.api.getBreakingNew(countryCode, pageNumber, API_KEY)
    }

    suspend fun searchNews(searchQuery:String, pageNumber: Int):Response<NewsResponse>{
        return RetrofitInstance.api.searchForNews(searchQuery,pageNumber, API_KEY)
    }

    suspend fun insertArticle(article: Article):Long{
        return db.getArticleDao().insertArticle(article)
    }

    fun getArticles():LiveData<List<Article>>{
        return db.getArticleDao().getArticles()
    }

    suspend fun deleteArticle(article: Article){
        db.getArticleDao().deleteArticle(article)
    }
}
package com.czchen.pottinews.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.czchen.pottinews.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article): Long

    @Query("select * from news_table")
    fun getArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

}
package sk.sandeep.newsfeedappmvvm.repository

import sk.sandeep.newsfeedappmvvm.db.NewsDatabase
import sk.sandeep.newsfeedappmvvm.dto_or_models.Article
import sk.sandeep.newsfeedappmvvm.network.RetrofitInstance

class NewsRepository(
    private val db: NewsDatabase
) {
    suspend fun getTopHeadlines(countryCode: String, pageNumber: Int) =
        RetrofitInstance.newsApi.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.newsApi.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticles(article)
}
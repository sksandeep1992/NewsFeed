package sk.sandeep.newsfeedappmvvm.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import sk.sandeep.newsfeedappmvvm.dto_or_models.NewsResponseDto
import sk.sandeep.newsfeedappmvvm.util_or_constants.Constants.Companion.API_KEY


/**
 * A public interface that exposes the [getBreakingNews] and [searchForNews] methods
 */
interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY,
    ): Response<NewsResponseDto>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY,
    ): Response<NewsResponseDto>
}



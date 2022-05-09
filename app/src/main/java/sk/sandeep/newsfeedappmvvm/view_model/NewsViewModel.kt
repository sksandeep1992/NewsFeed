package sk.sandeep.newsfeedappmvvm.view_model


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import sk.sandeep.newsfeedappmvvm.dto_or_models.Article
import sk.sandeep.newsfeedappmvvm.dto_or_models.NewsResponseDto
import sk.sandeep.newsfeedappmvvm.repository.NewsRepository
import sk.sandeep.newsfeedappmvvm.util_or_constants.Resource

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val topHeadlines: MutableLiveData<Resource<NewsResponseDto>> = MutableLiveData()
    private var topHeadlinesPage = 1

    val searchNews: MutableLiveData<Resource<NewsResponseDto>> = MutableLiveData()
    private var searchNewsPage = 1

    init {
        getTopHeadlinesNews("in")
    }

    private fun getTopHeadlinesNews(countryCode: String) = viewModelScope.launch {
        topHeadlines.postValue(Resource.Loading())
        val response = newsRepository.getTopHeadlines(countryCode, topHeadlinesPage)

        topHeadlines.postValue(handleTopHeadlinesResponse(response))
    }

    private fun handleTopHeadlinesResponse(response: Response<NewsResponseDto>):
            Resource<NewsResponseDto> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())

        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponseDto>):
            Resource<NewsResponseDto> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}
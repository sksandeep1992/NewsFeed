package sk.sandeep.newsfeedappmvvm.dto_or_models

data class NewsResponseDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
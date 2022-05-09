package sk.sandeep.newsfeedappmvvm.dto_or_models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


/**
 * Database entities go in this file. These are responsible for reading and writing from the
 * database.
 */

/**
 * Article represents a newsArticle entity in the database.
 */
@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
) :Serializable
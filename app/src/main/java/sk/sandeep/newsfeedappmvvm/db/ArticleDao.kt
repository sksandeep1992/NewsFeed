package sk.sandeep.newsfeedappmvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import sk.sandeep.newsfeedappmvvm.dto_or_models.Article

/**
 * Defines methods for using the article class with Room.
 */
@Dao
interface ArticleDao {

    /**
     * When Inserting a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param article new value to write
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    /**
     * Selects and returns all rows in the table,
     */
    @Query("SELECT * FROM article")
    fun getAllArticles(): LiveData<List<Article>>

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its conatents.
     */
    @Delete
    suspend fun deleteArticles(article: Article)
}
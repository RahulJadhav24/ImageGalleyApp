package com.rahul.imgur.data.local

import androidx.paging.DataSource
import androidx.room.*
import com.rahul.imgur.model.Images

@Database(entities = [Images::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imagesDao(): ImagesDao
}

@Dao
interface ImagesDao {
    @Query("SELECT * FROM images_table WHERE search_content = :search_content ORDER BY indexInResponse ASC")
    fun postsBySearchContents(search_content: String): DataSource.Factory<Int, Images>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(images: List<Images>)

    @Query("SELECT MAX(indexInResponse) FROM images_table WHERE search_content = :search_content")
    fun getNextIndexInSearch(search_content: String): Int


    @Query("SELECT MAX(pageNumber) FROM images_table WHERE search_content = :search_content")
    fun getNextPageInSearch(search_content: String): Int

    @Query("DELETE FROM images_table WHERE search_content = :search_content")
    fun deleteBySearchContents(search_content: String)

    @Query("DELETE FROM images_table")
    suspend fun deleteTable()

    @Query("SELECT COUNT(id) FROM images_table WHERE search_content = :search_content")
    fun getCount(search_content: String): Int
}



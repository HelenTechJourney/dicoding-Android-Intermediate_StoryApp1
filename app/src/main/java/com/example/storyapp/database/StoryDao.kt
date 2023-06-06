package com.example.storyapp.database

import androidx.room.*
import com.example.storyapp.remote.response.ListStoryItem

@Dao
interface StoryDao {

    @Query("SELECT* FROM stories")
    fun getAllListStories(): List<ListStoryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(stories: List<ListStoryItem>)

    @Query("DELETE FROM stories")
    fun deleteAll()
}
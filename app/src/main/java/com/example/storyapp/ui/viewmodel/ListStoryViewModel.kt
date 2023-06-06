package com.example.storyapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.database.StoryRepository
import com.example.storyapp.remote.response.ListStoryItem

class ListStoryViewModel(private val provideRepository: StoryRepository): ViewModel() {

    val message: LiveData<String> = provideRepository.message

    val isLoading: LiveData<Boolean> = provideRepository.isLoading

    val listUser: LiveData<List<ListStoryItem>?> = provideRepository.listUser

    fun getAllStories(token:String){
        provideRepository.getAllStories(token)
    }
}
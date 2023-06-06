package com.example.storyapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.database.StoryRepository
import com.example.storyapp.remote.response.Story

class DetailViewModel(private val provideRepository: StoryRepository) : ViewModel() {

    val detailUser: LiveData<Story> = provideRepository.detailUser

    val message: LiveData<String> = provideRepository.message

    val isLoading: LiveData<Boolean> = provideRepository.isLoading

    fun getDetail(token: String, id: String) {
        provideRepository.getDetail(token, id)
    }
}
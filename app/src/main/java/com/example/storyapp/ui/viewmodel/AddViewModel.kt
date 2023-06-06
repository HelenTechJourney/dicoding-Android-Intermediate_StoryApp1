package com.example.storyapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.database.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddViewModel(private val provideRepository: StoryRepository) : ViewModel() {
    val message: LiveData<String> = provideRepository.message

    val isLoading: LiveData<Boolean> = provideRepository.isLoading

    fun upload(
        photo: MultipartBody.Part,
        desc: RequestBody,
        token: String
    ){
        provideRepository.upload(photo,desc,token)
    }
}
package com.example.storyapp.database

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.remote.response.*
import com.example.storyapp.remote.retrofit.ApiConfig
import com.example.storyapp.remote.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository(
private val storyDatabase: StoryDatabase,
private val apiService: ApiService
) {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userLogin = MutableLiveData<LoginResponse>()
    var userLogin: LiveData<LoginResponse> = _userLogin

    private val _listUser = MutableLiveData<List<ListStoryItem>>()
    val listUser: LiveData<List<ListStoryItem>?> = _listUser

    private val _detailUser = MutableLiveData<Story>()
    val detailUser: LiveData<Story> = _detailUser

    fun getLoginResponse(requestLogin: RequestLogin) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().accessAccount(requestLogin)
        api.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    _userLogin.value = responseBody!!
                    _message.value = "Login as ${_userLogin.value!!.loginResult.name}"
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getSignupResponse(requestSignup: RequestSignup) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().createAccount(requestSignup)
        api.enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    _message.value = responseBody?.message.toString()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getAllStories(token:String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getAllStories( "Bearer $token")

        api.enqueue(object : Callback<ListStoryResponse> {
            override fun onResponse(
                call: Call<ListStoryResponse>,
                response: Response<ListStoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = responseBody.listStory
                    } else {
                        Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<ListStoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getDetail(token: String, id:String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getDetailUser("Bearer $token", id)

        api.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _detailUser.value = responseBody?.story
                } else {
                    Log.e(ContentValues.TAG, "onFailure1: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure2: ${t.message}")
            }
        })
    }

    fun upload(
        photo: MultipartBody.Part,
        description: RequestBody,
        token: String,
    ) {
        _isLoading.value = true
        val service = ApiConfig.getApiService().addNewStories(
            "Bearer $token", description, photo
        )
        service.enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _message.value = responseBody.message
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}
package com.example.storyapp.remote.retrofit

import com.example.storyapp.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("register")
    fun createAccount(@Body requestSignUp: RequestSignup):
            Call<ResponseMessage>

    @POST("login")
    fun accessAccount(
        @Body requestLogin: RequestLogin
    ): Call<LoginResponse>

    @Multipart
    @POST("stories")
    fun addNewStories(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part
    ): Call<ResponseMessage>

    @GET("stories")
   fun getAllStories(
        @Header("Authorization") token: String
    ): Call<ListStoryResponse>

    @GET("stories/{id}")
    fun getDetailUser(
        @Header("Authorization") token : String,
        @Path("id") id: String
    ): Call<DetailResponse>
}
package com.example.cyber2.network

import com.example.cyber2.models.ImgurResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImgurSevice{
    @Multipart
    @POST("3/image")
    suspend fun uploadImage(
        @Part image : MultipartBody.Part,
        @Header("Authorization") auth : String
    ): Response<ImgurResponse>

}
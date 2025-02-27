package com.example.data.remote

import com.example.data.model.TokenServerModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("android/wallet/")
    suspend fun getTokens(): Response<List<TokenServerModel>>
}
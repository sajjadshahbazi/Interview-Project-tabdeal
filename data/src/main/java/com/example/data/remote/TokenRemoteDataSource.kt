package com.example.data.remote

import com.example.data.model.TokenServerModel
import javax.inject.Inject
import  com.example.common.model.Result

class TokenRemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getTokens(): Result<List<TokenServerModel>> {
        return try {
            val response = apiService.getTokens()
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

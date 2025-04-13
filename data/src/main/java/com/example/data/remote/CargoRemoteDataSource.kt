package com.example.data.remote

import javax.inject.Inject
import  com.example.common.model.Result
import com.example.data.model.CargoServerModel

class CargoRemoteDataSource @Inject constructor(private val apiService: CargoApiService) {
    suspend fun getCargos(): Result<List<CargoServerModel>> {
        return try {
            val response = apiService.getCargos()
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

package com.example.data.remote

import com.example.data.model.CargoServerModel
import retrofit2.Response
import retrofit2.http.GET

interface CargoApiService {
    @GET("cargo/")
    suspend fun getCargos(): Response<List<CargoServerModel>>
}
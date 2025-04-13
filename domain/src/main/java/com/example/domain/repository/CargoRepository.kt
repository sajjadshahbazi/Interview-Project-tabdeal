package com.example.domain.repository

import com.example.common.model.Result
import com.example.domain.model.CargoRepoModel

interface CargoRepository {
    suspend fun getCargos(): Result<List<CargoRepoModel>>
}
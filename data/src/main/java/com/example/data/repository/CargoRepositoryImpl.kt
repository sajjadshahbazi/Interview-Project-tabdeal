package com.example.data.repository

import com.example.common.model.Result
import com.example.data.model.CargoServerModel
import javax.inject.Inject
import com.example.data.remote.CargoRemoteDataSource
import com.example.domain.model.CargoRepoModel
import com.example.domain.repository.CargoRepository

class CargoRepositoryImpl @Inject constructor(
    private val remoteDataSource: CargoRemoteDataSource
) : CargoRepository {

    override suspend fun getCargos(): Result<List<CargoRepoModel>> {
        return try {
            val cargoResult = remoteDataSource.getCargos()
            if (cargoResult is Result.Success<List<CargoServerModel>>) {
                Result.Success(cargoResult.data.map { it.toRepoModel() })
            } else {
                Result.Error(Exception("Unkown"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

fun CargoServerModel.toRepoModel(): CargoRepoModel {
    return CargoRepoModel(
        id = this.id ?: -1,
        origin = this.origin ?: "",
        destination = this.destination ?: "",
        price = this.price ?: "",
        packaging = this.packaging ?: "",
        weight = this.weight ?: "",
        type = this.type ?: "",
        loadingDate = this.loadingDate ?: "",
        isSelected = false
    )
}
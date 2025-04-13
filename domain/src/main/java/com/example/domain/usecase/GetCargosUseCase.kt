package com.example.domain.usecase


import com.example.common.model.Result
import com.example.domain.model.CargoRepoModel
import com.example.domain.repository.CargoRepository
import javax.inject.Inject

class GetCargosUseCase @Inject constructor(private val cargoRepository: CargoRepository) {
    suspend operator fun invoke(): Result<List<CargoRepoModel>> {
        return cargoRepository.getCargos()
    }
}
package com.example.domain.usecase


import com.example.common.model.Result
import com.example.domain.model.TokenRepoModel
import com.example.domain.repository.TokenRepository
import javax.inject.Inject

class GetTokensUseCase @Inject constructor(private val assetRepository: TokenRepository) {
    suspend operator fun invoke(): Result<List<TokenRepoModel>> {
        return assetRepository.getTokens()
    }
}
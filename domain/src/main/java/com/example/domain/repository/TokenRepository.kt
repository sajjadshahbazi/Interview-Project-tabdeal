package com.example.domain.repository

import com.example.common.model.Result
import com.example.domain.model.TokenRepoModel

interface TokenRepository {
    suspend fun getTokens(): Result<List<TokenRepoModel>>
}
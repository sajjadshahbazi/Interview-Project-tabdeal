package com.example.data.repository

import com.example.common.model.Result
import com.example.data.remote.TokenRemoteDataSource
import com.example.domain.model.TokenRepoModel
import com.example.domain.repository.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.data.model.TokenServerModel

class TokenRepositoryImpl @Inject constructor(private val remoteDataSource: TokenRemoteDataSource) : TokenRepository {

    override suspend fun getTokens(): Result<List<TokenRepoModel>> = withContext(Dispatchers.IO) {
        try {
            val tokenResult = remoteDataSource.getTokens()
            if (tokenResult is Result.Success<List<TokenServerModel>>) {
                Result.Success(tokenResult.data.map { it.toRepoModel() })
            } else {
                Result.Error(Exception("Unkown"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

fun TokenServerModel.toRepoModel(): TokenRepoModel {
    return TokenRepoModel(
        secondCurrencySymbol = second_currency_symbol ?: "",
        currencyName = currency_name ?: "",
        currencyNameFa = currency_name_fa ?: "",
        currencyRepresentationName = currency_representation_name ?: "",
        currencySymbol = currency_symbol ?: "",
        currencyId = currency_id ?: 0,
        currencyMaxDustAmount = currency_max_dust_amount ?: "",
        credit = credit ?: "",
        availableCredit = available_credit ?: "",
        usdtValue = usdt_value ?: 0.0,
        irtValue = irt_value ?: 0.0,
        relatedMarkets = related_markets ?: emptyList(),
        colors = this.colors?.let {
            TokenRepoModel.ColorsRepoModel(
                it.primary ?: "",
                it.secondary ?: ""
            )
        } ?: TokenRepoModel.ColorsRepoModel("", ""),
        banksAccounts = this.banks_accounts?.map {
            TokenRepoModel.BankAccountRepoModel(
                it?.sheba ?: "", it?.card_number ?: ""
            )
        } ?: emptyList()
    )
}
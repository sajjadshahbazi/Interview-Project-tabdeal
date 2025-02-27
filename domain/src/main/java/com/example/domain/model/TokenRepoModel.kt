package com.example.domain.model

data class TokenRepoModel(
    val secondCurrencySymbol: String,
    val currencyName: String,
    val currencyNameFa: String,
    val currencyRepresentationName: String,
    val currencySymbol: String,
    val currencyId: Int,
    val currencyMaxDustAmount: String,
    val credit: String,
    val availableCredit: String,
    val usdtValue: Double,
    val irtValue: Double,
    val relatedMarkets: List<String>,
    val colors: ColorsRepoModel,
    val banksAccounts: List<BankAccountRepoModel> = emptyList()
) {
    data class ColorsRepoModel(
        val primary: String,
        val secondary: String
    )

    data class BankAccountRepoModel(
        val sheba: String,
        val cardNumber: String
    )
}
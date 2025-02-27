package com.example.data.model

data class TokenServerModel(
    val second_currency_symbol: String?,
    val currency_name: String?,
    val currency_name_fa: String?,
    val currency_representation_name: String?,
    val currency_symbol: String?,
    val currency_id: Int?,
    val currency_max_dust_amount: String?,
    val credit: String?,
    val available_credit: String?,
    val usdt_value: Double?,
    val irt_value: Double?,
    val related_markets: List<String>?,
    val colors: ColorsServerModel?,
    val banks_accounts: List<BankAccountServerModel?>? = null
)

data class ColorsServerModel(
    val primary: String?,
    val secondary: String?
)

data class BankAccountServerModel(
    val sheba: String?,
    val card_number: String?
)
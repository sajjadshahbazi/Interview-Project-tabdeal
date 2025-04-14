package com.example.data.model

data class CargoServerModel(
    val id: Int?=-1,
    val origin: String?="",
    val destination: String?="",
    val weight: String?="",
    val type: String?="",
    val packaging: String?="",
    val loadingDate: String?="",
    val price: String?=""
)
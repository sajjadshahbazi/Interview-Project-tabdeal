package com.example.domain.model

data class CargoRepoModel (
    val id: Int,
    val origin: String,
    val destination: String,
    val weight: String,
    val type: String,
    val packaging: String,
    val loadingDate: String,
    val price: String,
    var isSelected: Boolean = false
)
package com.example.scooterx.domain.entities

import java.io.Serializable

data class Scooter(
    val name: String,
    val battery: String,
    val plaka: String,
    val range: String,
    val speed: String,
    val fee: String,
    val latitude: String,
    val longitude: String
) : Serializable {
    constructor() : this("","","", "", "", "", "", "")
}
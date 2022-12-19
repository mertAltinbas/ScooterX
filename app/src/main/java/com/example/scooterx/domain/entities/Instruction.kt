package com.example.scooterx.domain.entities


data class Instruction(
    val slogan: String, val privacy: String, val image: String,

    ) : java.io.Serializable {
    constructor() : this("", "", "")
}


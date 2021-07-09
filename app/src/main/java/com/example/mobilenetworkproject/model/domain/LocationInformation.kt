package com.example.mobilenetworkproject.model.domain

data class LocationInformation (
    val cellId: Long,
    val LocationLongitude: Double,
    val LocationLatitude: Double,
    val cellPLMN: String,
    val ping: String,
    val jitter: String,
    val downLinkRate: String,
    val upLinkRate: String,
    val RssiRXlev: String,
    val RsrpRsrq: String
)
package com.example.mobilenetworkproject.model.domain


data class CellInformation (
//    val name:String,
//    val emailAddress:String,
//    val studentID:String,
    val cellGeneration: String,
    val cellId: Long,
    val cellPLMN: String,
    val cellARFCN: Int,
    val cellLac: Int,
    val cellCode: Int,
)
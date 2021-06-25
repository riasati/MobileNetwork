package com.example.mobilenetworkproject.model.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cell_information")
data class CellInformationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "generation")
    val cellGeneration: String,
    @ColumnInfo(name = "cell_id")
    val cellId: Long,
    @ColumnInfo(name = "PLMN")
    val cellPLMN: String,
    @ColumnInfo(name = "ARFCN")
    val cellARFCN: Int,
    @ColumnInfo(name = "LAC_TAC")
    val cellLac: Int,
    @ColumnInfo(name = "BSIC_PSC_PCI")
    val cellCode: Int,
)
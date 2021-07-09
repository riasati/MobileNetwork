package com.example.mobilenetworkproject.model.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_information")
data class LocationInformationEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "cell_id")
    val cellId: Long,
    @ColumnInfo(name = "Longitude")
    val LocationLongitude: Double,
    @ColumnInfo(name = "Latitude")
    val LocationLatitude: Double,
    @ColumnInfo(name = "cell_plmn")
    val cellPLMN: String,
    @ColumnInfo(name = "ping")
    val ping: String,
    @ColumnInfo(name = "jitter")
    val jitter: String,
    @ColumnInfo(name = "down_link_rate")
    val downLinkRate: String,
    @ColumnInfo(name = "up_link_rate")
    val upLinkRate: String,
    @ColumnInfo(name = "rssi_rxlev")
    val RssiRXlev: String,
    @ColumnInfo(name = "rsrp_rsrq")
    val RsrpRsrq: String,

)
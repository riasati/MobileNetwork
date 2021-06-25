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

)
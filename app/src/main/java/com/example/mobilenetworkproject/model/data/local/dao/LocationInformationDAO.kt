package com.example.mobilenetworkproject.model.data.local.dao

import androidx.room.*
import com.example.mobilenetworkproject.model.data.local.entity.LocationInformationEntity

@Dao
interface LocationInformationDAO {
    @Query("SELECT * FROM location_information")
    fun selectAllLocationInformation(): List<LocationInformationEntity>

    @Insert
    fun insertLocationInformation(locationInformation: LocationInformationEntity)

    @Delete
    fun deleteLocationInformation(locationInformation: LocationInformationEntity)

    @Update
    fun updateLocationInformation(locationInformation: LocationInformationEntity): Int
}
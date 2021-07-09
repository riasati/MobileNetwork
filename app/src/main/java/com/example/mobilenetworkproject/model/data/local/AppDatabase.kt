package com.example.mobilenetworkproject.model.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilenetworkproject.model.data.local.dao.LocationInformationDAO
import com.example.mobilenetworkproject.model.data.local.entity.LocationInformationEntity

@Database(entities = [LocationInformationEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun locationInformationDAO(): LocationInformationDAO
}
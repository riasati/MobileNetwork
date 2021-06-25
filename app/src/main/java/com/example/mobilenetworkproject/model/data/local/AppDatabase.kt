package com.example.mobilenetworkproject.model.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilenetworkproject.model.data.local.dao.CellInformationDAO
import com.example.mobilenetworkproject.model.data.local.entity.CellInformationEntity

@Database(entities = [CellInformationEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun cellInformationDAO(): CellInformationDAO

}
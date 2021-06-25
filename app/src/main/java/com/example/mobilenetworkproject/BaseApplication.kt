package com.example.mobilenetworkproject

import android.app.Application
import com.example.mobilenetworkproject.model.data.local.AppDatabase
import com.example.mobilenetworkproject.model.data.local.AppDatabaseAdapter

class BaseApplication: Application() {
    companion object{
        lateinit var appDatabase: AppDatabase
    }


    override fun onCreate() {
        super.onCreate()
        appDatabase = AppDatabaseAdapter.database(this)
    }
}
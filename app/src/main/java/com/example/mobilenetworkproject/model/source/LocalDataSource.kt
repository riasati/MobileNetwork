package com.example.mobilenetworkproject.model.source

import com.example.mobilenetworkproject.model.entity.TouristPlace

object LocalDataSource{
    fun selectTouristPlace(): Array<TouristPlace> {
        val tp1 = TouristPlace(name = "Tadlkj", color = "blue")
        val tp2 = TouristPlace(name = "Tadlkj", color = "blue")
        return arrayOf(tp1, tp2)
    }
}
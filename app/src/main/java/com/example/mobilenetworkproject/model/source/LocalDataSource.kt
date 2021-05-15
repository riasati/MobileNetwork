package com.example.mobilenetworkproject.model.source

import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.model.entity.TouristPlaceModel

object LocalDataSource{
    fun selectTouristPlace(): Array<TouristPlaceModel> {
        val tp1 = TouristPlaceModel(name = "Tadlkj", country = "blue", star = 4.3F, imageResourceId = R.drawable.image1)
        val tp2 = TouristPlaceModel(name = "Tadlkj", country = "blue", star = 4.1F, imageResourceId = R.drawable.image2)
        return arrayOf(tp1, tp2)
    }
}
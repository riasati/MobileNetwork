package com.example.mobilenetworkproject.model.source

import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.model.entity.TouristPlaceModel

object LocalDataSource {
    fun selectTouristPlace(): Array<TouristPlaceModel> {
        val tp1 = TouristPlaceModel(
            name = "IMAMZADEH SALEH",
            country = "IRAN",
            star = 4.3F,
            imageResourceId = R.drawable.tp1
        )
        val tp2 = TouristPlaceModel(
            name = "NASIR OL MOLK MOSQUE",
            country = "IRAN",
            star = 4.1F,
            imageResourceId = R.drawable.tp2
        )
        val tp3 = TouristPlaceModel(
            name = "SAYYED ALAEDDIN HOSSEIN SHRINE",
            country = "IRAN",
            star = 4.1F,
            imageResourceId = R.drawable.tp3
        )
        val tp4 = TouristPlaceModel(
            name = "ERAM GARDEN",
            country = "IRAN",
            star = 4.1F,
            imageResourceId = R.drawable.tp4
        )
        val tp5 = TouristPlaceModel(
            name = "MAHARLOO LAKE",
            country = "IRAN",
            star = 4.1F,
            imageResourceId = R.drawable.tp5
        )
        val tp6 = TouristPlaceModel(
            name = "PERSEPOLIS",
            country = "IRAN",
            star = 4.1F,
            imageResourceId = R.drawable.tp6
        )
        val tp7 = TouristPlaceModel(
            name = "NAQSH-E ROSTAM",
            country = "IRAN",
            star = 4.1F,
            imageResourceId = R.drawable.tp7
        )
        val tp8 = TouristPlaceModel(
            name = "RAYEN CASTLE",
            country = "IRAN",
            star = 4.1F,
            imageResourceId = R.drawable.tp8
        )
        val tp9 = TouristPlaceModel(
            name = "YAZD OLD TOWN",
            country = "IRAN",
            star = 4.1F,
            imageResourceId = R.drawable.tp9
        )
        val tp10 = TouristPlaceModel(
            name = "MOLLABASHI HOUSE",
            country = "IRAN",
            star = 4.1F,
            imageResourceId = R.drawable.tp10
        )
        return arrayOf(tp1, tp2, tp3, tp4, tp5, tp6, tp7, tp8, tp9, tp10)
    }
}
package com.example.mobilenetworkproject.model.entity

import androidx.annotation.DrawableRes

data class TouristPlaceModel (
    val id:Int,
    val name:String,
    val country:String,
    val star: Float,
    val description: String,
    @DrawableRes val imageResourceId: Int
)
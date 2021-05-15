package com.example.mobilenetworkproject.model.repository

import com.example.mobilenetworkproject.model.entity.TouristPlaceModel
import com.example.mobilenetworkproject.model.source.LocalDataSource

object HomeDataRepository{
   fun getTouristPlace(): Array<TouristPlaceModel>{
      return LocalDataSource.selectTouristPlace()
   }
}
package com.example.mobilenetworkproject.model.repository

import com.example.mobilenetworkproject.model.entity.TouristPlace
import com.example.mobilenetworkproject.model.source.LocalDataSource

object HomeDataRepository{
   fun getTouristPlace(): Array<TouristPlace>{
      return LocalDataSource.selectTouristPlace()
   }
}
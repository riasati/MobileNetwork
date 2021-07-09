package com.example.mobilenetworkproject.model.data.repository

import com.example.mobilenetworkproject.model.domain.LocationInformation

interface HomeDataRepository {
    fun insertLocationInformation(locationInformation: LocationInformation)
}
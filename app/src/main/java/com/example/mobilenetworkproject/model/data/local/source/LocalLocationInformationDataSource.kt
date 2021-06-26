package com.example.mobilenetworkproject.model.data.local.source

import com.example.mobilenetworkproject.model.domain.LocationInformation


interface LocalLocationInformationDataSource {
    fun selectAllLocationInformation(): List<LocationInformation>?

    fun insertLocationInformation(locationInformation: LocationInformation)
}
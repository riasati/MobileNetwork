package com.example.mobilenetworkproject.model.data.repository.impl

import com.example.mobilenetworkproject.model.data.local.source.impl.LocalLocationInformationDataSourceImpl
import com.example.mobilenetworkproject.model.data.repository.HomeDataRepository
import com.example.mobilenetworkproject.model.domain.LocationInformation

object HomeDataRepositoryImpl: HomeDataRepository{
    override fun insertLocationInformation(locationInformation: LocationInformation) {
        LocalLocationInformationDataSourceImpl.insertLocationInformation(locationInformation)
    }

}
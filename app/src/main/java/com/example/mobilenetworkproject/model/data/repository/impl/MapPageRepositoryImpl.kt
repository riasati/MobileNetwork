package com.example.mobilenetworkproject.model.data.repository.impl

import com.example.mobilenetworkproject.model.data.local.source.impl.LocalLocationInformationDataSourceImpl
import com.example.mobilenetworkproject.model.data.repository.MapPageRepository
import com.example.mobilenetworkproject.model.domain.LocationInformation

object MapPageRepositoryImpl: MapPageRepository {
    override fun selectAllLocationInformation(): List<LocationInformation>? {
        return LocalLocationInformationDataSourceImpl.selectAllLocationInformation()
    }



}
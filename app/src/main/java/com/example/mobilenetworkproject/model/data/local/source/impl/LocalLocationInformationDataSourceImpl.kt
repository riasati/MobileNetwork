package com.example.mobilenetworkproject.model.data.local.source.impl

import com.example.mobilenetworkproject.BaseApplication
import com.example.mobilenetworkproject.model.data.local.source.LocalLocationInformationDataSource
import com.example.mobilenetworkproject.model.domain.LocationInformation
import com.example.mobilenetworkproject.model.data.local.mapper.LocationInformationMapper
//import com.example.mobilenetworkproject.model.data.local.source.LocalLocationInformationDataSource
//import com.example.mobilenetworkproject.model.data.local.dao.LocationInformationDAO

object LocalLocationInformationDataSourceImpl : LocalLocationInformationDataSource {
    override fun selectAllLocationInformation(): List<LocationInformation>? {
        val appDatabase = BaseApplication.appDatabase
        val locationsInformation = appDatabase.locationInformationDAO().selectAllLocationInformation()?: return null
        return locationsInformation.map {
                locationInformationEntity-> LocationInformationMapper.mapEntityToDomain(locationInformationEntity)
        }
    }

    override fun insertLocationInformation(locationInformation: LocationInformation) {
        val appDatabase = BaseApplication.appDatabase
        appDatabase.locationInformationDAO().insertLocationInformation(LocationInformationMapper.mapDomainToEntity(locationInformation))
    }

}
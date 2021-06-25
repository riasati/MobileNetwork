package com.example.mobilenetworkproject.model.data.local.mapper

import com.example.mobilenetworkproject.model.data.local.entity.LocationInformationEntity
import com.example.mobilenetworkproject.model.domain.LocationInformation

object LocationInformationMapper {
    fun mapEntityToDomain(from: LocationInformationEntity): LocationInformation {
        return LocationInformation(cellId = from.cellId, LocationLongitude = from.LocationLongitude, LocationLatitude = from.LocationLatitude)
    }

    fun mapDomainToEntity(from: LocationInformation): LocationInformationEntity {
        return LocationInformationEntity(null, cellId = from.cellId, LocationLongitude = from.LocationLongitude, LocationLatitude = from.LocationLatitude)
    }
}
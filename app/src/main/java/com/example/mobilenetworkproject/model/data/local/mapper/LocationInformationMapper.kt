package com.example.mobilenetworkproject.model.data.local.mapper

import com.example.mobilenetworkproject.model.data.local.entity.LocationInformationEntity
import com.example.mobilenetworkproject.model.domain.LocationInformation

object LocationInformationMapper {
    fun mapEntityToDomain(from: LocationInformationEntity): LocationInformation {
        return LocationInformation(
            cellId = from.cellId,
            LocationLongitude = from.LocationLongitude,
            LocationLatitude = from.LocationLatitude,
            cellPLMN = from.cellPLMN,
            ping = from.ping,
            jitter = from.jitter,
            downLinkRate = from.downLinkRate,
            upLinkRate = from.upLinkRate,
            RsrpRsrq = from.RsrpRsrq,
            RssiRXlev = from.RssiRXlev
        )
    }

    fun mapDomainToEntity(from: LocationInformation): LocationInformationEntity {
        return LocationInformationEntity(
            null,
            cellId = from.cellId,
            LocationLongitude = from.LocationLongitude,
            LocationLatitude = from.LocationLatitude,
            cellPLMN = from.cellPLMN,
            ping = from.ping,
            jitter = from.jitter,
            downLinkRate = from.downLinkRate,
            upLinkRate = from.upLinkRate,
            RsrpRsrq = from.RsrpRsrq,
            RssiRXlev = from.RssiRXlev
        )
    }
}
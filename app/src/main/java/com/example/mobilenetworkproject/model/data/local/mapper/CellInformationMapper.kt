package com.example.mobilenetworkproject.model.data.local.mapper

import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.data.local.entity.CellInformationEntity

object CellInformationMapper {
    fun mapEntityToDomain(from: CellInformationEntity): CellInformation {
        return CellInformation(name = from.name, studentID = from.studentID, emailAddress = from.emailAddress)
    }

    fun mapDomainToEntity(from: CellInformation): CellInformationEntity{
        return CellInformationEntity(null, name = from.name, studentID = from.studentID, emailAddress = from.emailAddress)
    }
}
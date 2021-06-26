package com.example.mobilenetworkproject.model.data.local.mapper

import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.data.local.entity.CellInformationEntity

object CellInformationMapper {
    fun mapEntityToDomain(from: CellInformationEntity): CellInformation {
        //if (from != null) {
            return CellInformation(cellGeneration = from.cellGeneration, cellId = from.cellId, cellPLMN = from.cellPLMN, cellARFCN = from.cellARFCN, cellLac = from.cellLac, cellCode = from.cellCode)
       // }
       // return null
    }

    fun mapDomainToEntity(from: CellInformation): CellInformationEntity{
        return CellInformationEntity(null, cellGeneration = from.cellGeneration, cellId = from.cellId, cellPLMN = from.cellPLMN, cellARFCN = from.cellARFCN, cellLac = from.cellLac, cellCode = from.cellCode)
    }
}
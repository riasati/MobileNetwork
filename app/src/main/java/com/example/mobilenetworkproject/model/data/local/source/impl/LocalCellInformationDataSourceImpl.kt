package com.example.mobilenetworkproject.model.data.local.source.impl

import com.example.mobilenetworkproject.BaseApplication
import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.data.local.mapper.CellInformationMapper
import com.example.mobilenetworkproject.model.data.local.source.LocalCellInformationDataSource

object LocalCellInformationDataSourceImpl: LocalCellInformationDataSource {
    override fun selectAllCellInformation(): List<CellInformation> {
        val appDatabase = BaseApplication.appDatabase
        return appDatabase.cellInformationDAO().selectAllCelInformation().map {
            cellInformationEntity-> CellInformationMapper.mapEntityToDomain(cellInformationEntity)
        }
    }
    override fun insertCellInformation(cellInformation: CellInformation){
        val appDatabase = BaseApplication.appDatabase
        appDatabase.cellInformationDAO().insertCellInformation(CellInformationMapper.mapDomainToEntity(cellInformation))
    }

    override fun getCellInformation(cell_information_id: Int): CellInformation {
        val appDatabase = BaseApplication.appDatabase
        return CellInformationMapper.mapEntityToDomain(appDatabase.cellInformationDAO().getCellInformation(cell_information_id))
    }
}
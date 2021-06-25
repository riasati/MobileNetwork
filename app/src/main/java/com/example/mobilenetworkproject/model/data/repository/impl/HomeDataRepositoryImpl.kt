package com.example.mobilenetworkproject.model.data.repository.impl

import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.data.local.source.impl.LocalCellInformationDataSourceImpl
import com.example.mobilenetworkproject.model.data.local.source.impl.LocalLocationInformationDataSourceImpl
import com.example.mobilenetworkproject.model.data.repository.HomeDataRepository
import com.example.mobilenetworkproject.model.domain.LocationInformation

object HomeDataRepositoryImpl: HomeDataRepository{
    override fun insertCellInformation(cellInformation: CellInformation) {
        LocalCellInformationDataSourceImpl.insertCellInformation(cellInformation)
    }
    override fun insertLocationInformation(locationInformation: LocationInformation) {
        LocalLocationInformationDataSourceImpl.insertLocationInformation(locationInformation)
    }

    override fun getCelInformationByCellId(cell_id: Long): Long {
        return LocalCellInformationDataSourceImpl.getCelInformationByCellId(cell_id)
    }

}
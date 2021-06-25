package com.example.mobilenetworkproject.model.data.repository.impl

import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.data.local.source.impl.LocalCellInformationDataSourceImpl
import com.example.mobilenetworkproject.model.data.repository.HomeDataRepository

object HomeDataRepositoryImpl: HomeDataRepository{
    override fun insertCellInformation(cellInformation: CellInformation) {
        LocalCellInformationDataSourceImpl.insertCellInformation(cellInformation)
    }

}
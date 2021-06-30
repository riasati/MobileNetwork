package com.example.mobilenetworkproject.model.data.repository.impl

import com.example.mobilenetworkproject.model.data.local.source.impl.LocalCellInformationDataSourceImpl
import com.example.mobilenetworkproject.model.data.local.source.impl.LocalLocationInformationDataSourceImpl
import com.example.mobilenetworkproject.model.data.repository.ChoiceMapRepository
import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.domain.LocationInformation

object ChoiceMapRepositoryImpl: ChoiceMapRepository {
    override fun getCellInformationByCellId(cell_id: Long): CellInformation? {
        return LocalCellInformationDataSourceImpl.getCelInformationByCellId(cell_id)
    }

    override fun selectAllCellsInformation(): List<CellInformation>? {
        return LocalCellInformationDataSourceImpl.selectAllCellInformation()
    }

    override fun selectAllLocationInformation(): List<LocationInformation>? {
        return LocalLocationInformationDataSourceImpl.selectAllLocationInformation()
    }



}
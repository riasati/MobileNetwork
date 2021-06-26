package com.example.mobilenetworkproject.model.data.local.source

import com.example.mobilenetworkproject.model.domain.CellInformation

interface LocalCellInformationDataSource {

    fun selectAllCellInformation(): List<CellInformation>?

    fun insertCellInformation(cellInformation: CellInformation)

    fun getCelInformationByCellId(cell_id: Long): CellInformation?

}
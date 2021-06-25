package com.example.mobilenetworkproject.model.data.local.source

import com.example.mobilenetworkproject.model.domain.CellInformation

interface LocalCellInformationDataSource {
    fun selectAllCellInformation(): List<CellInformation>

    fun insertCellInformation(cellInformation: CellInformation)

    fun getCellInformation(cell_information_id: Int): CellInformation
}
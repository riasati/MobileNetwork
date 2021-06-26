package com.example.mobilenetworkproject.model.data.repository

import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.domain.LocationInformation

interface MapDataRepository {
    fun getCellInformationByCellId(cell_id: Long): CellInformation

    fun selectAllCellsInformation(): List<CellInformation>

    fun selectAllLocationInformation(): List<LocationInformation>
}
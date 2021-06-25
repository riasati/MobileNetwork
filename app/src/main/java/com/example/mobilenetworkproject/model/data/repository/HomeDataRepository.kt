package com.example.mobilenetworkproject.model.data.repository

import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.domain.LocationInformation

interface HomeDataRepository {
    fun insertCellInformation(cellInformation: CellInformation)
    fun insertLocationInformation(locationInformation: LocationInformation)
    fun getCelInformationByCellId(cell_id : Long): Long
}
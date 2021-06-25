package com.example.mobilenetworkproject.model.data.repository

import com.example.mobilenetworkproject.model.domain.CellInformation

interface HomeDataRepository {
    fun insertCellInformation(cellInformation: CellInformation)
}
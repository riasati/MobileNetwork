package com.example.mobilenetworkproject.ui.choiceMpaPage

import androidx.lifecycle.ViewModel
import com.example.mobilenetworkproject.model.data.repository.impl.ChoiceMapRepositoryImpl
import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.domain.LocationInformation

class ChoicePageViewModel : ViewModel() {
    fun getCellInformationByCellId(cell_id: Long): CellInformation?{
        return ChoiceMapRepositoryImpl.getCellInformationByCellId(cell_id)
    }

    fun selectAllCellsInformation(): List<CellInformation>?{
        return ChoiceMapRepositoryImpl.selectAllCellsInformation()?: arrayListOf<CellInformation>()
    }

    fun selectAllLocationInformation(): List<LocationInformation>?{
        return ChoiceMapRepositoryImpl.selectAllLocationInformation()?: arrayListOf<LocationInformation>()
    }

}
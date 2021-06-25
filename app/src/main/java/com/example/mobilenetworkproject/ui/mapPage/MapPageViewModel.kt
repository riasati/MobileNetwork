package com.example.mobilenetworkproject.ui.mapPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilenetworkproject.model.data.repository.impl.MapDataRepositoryImpl
import com.example.mobilenetworkproject.model.domain.CellInformation

class MapPageViewModel : ViewModel() {
    fun getCellInformationByCellId(cell_information_id: Int): CellInformation{
        return MapDataRepositoryImpl.getCellInformation(cell_information_id)
    }

    fun selectAllCellsInformation(): List<CellInformation>{
        return MapDataRepositoryImpl.selectAllCellsInformation()
    }
}
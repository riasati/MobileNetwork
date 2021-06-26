package com.example.mobilenetworkproject.ui.mapPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilenetworkproject.model.data.repository.impl.MapDataRepositoryImpl
import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.domain.LocationInformation

class MapPageViewModel : ViewModel() {
    fun getCellInformationByCellId(cell_id: Long): CellInformation{
        return MapDataRepositoryImpl.getCellInformationByCellId(cell_id)
    }

    fun selectAllCellsInformation(): List<CellInformation>{
        return MapDataRepositoryImpl.selectAllCellsInformation()
    }

    fun selectAllLocationInformation(): List<LocationInformation>{
        return MapDataRepositoryImpl.selectAllLocationInformation()
    }
}
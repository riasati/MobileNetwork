package com.example.mobilenetworkproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilenetworkproject.model.data.repository.impl.HomeDataRepositoryImpl
//import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.domain.LocationInformation

//import com.example.mobilenetworkproject.model.data.repository.impl.MapDataRepositoryImpl
//import com.example.mobilenetworkproject.model.domain.CellInformation

class HomeViewModel : ViewModel() {
//    fun insertCellInformation(cellInfo : CellInformation){
//        HomeDataRepositoryImpl.insertCellInformation(cellInfo)
//    }
    fun insertLocationInformation(LocationInfo : LocationInformation){
        HomeDataRepositoryImpl.insertLocationInformation(LocationInfo)
    }
//    fun getCelInformationByCellId(cell_id : Long) : CellInformation?{
//        return HomeDataRepositoryImpl.getCelInformationByCellId(cell_id)
//    }

}
package com.example.mobilenetworkproject.ui.mapPage

import androidx.lifecycle.ViewModel
import com.example.mobilenetworkproject.model.data.repository.impl.MapPageRepositoryImpl
import com.example.mobilenetworkproject.model.domain.LocationInformation

class MapPageViewModel : ViewModel() {
    fun selectAllLocationInformation(): List<LocationInformation>?{
        return MapPageRepositoryImpl.selectAllLocationInformation()?: arrayListOf<LocationInformation>()
    }

}
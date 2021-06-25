package com.example.mobilenetworkproject.ui.mapPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilenetworkproject.model.data.repository.impl.MapDataRepositoryImpl

class MapPageViewModel : ViewModel() {
    private val _cellPlace = MutableLiveData<Array<StudentModel>>().apply {
        value = MapDataRepositoryImpl.
    }
    val studentModel: LiveData<Array<StudentModel>> = _studentModel
}
package com.example.mobilenetworkproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilenetworkproject.model.entity.TouristPlaceModel
import com.example.mobilenetworkproject.model.repository.HomeDataRepository

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _touristPlacesModel: MutableLiveData<Array<TouristPlaceModel>> = MutableLiveData<Array<TouristPlaceModel>>().apply {
        value = HomeDataRepository.getTouristPlace()
    }

    val touristPlacesModel: LiveData<Array<TouristPlaceModel>> = _touristPlacesModel
}
package com.example.mobilenetworkproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilenetworkproject.model.entity.TouristPlace
import com.example.mobilenetworkproject.model.repository.HomeDataRepository

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _touristPlaces: MutableLiveData<Array<TouristPlace>> = MutableLiveData<Array<TouristPlace>>().apply {
        value = HomeDataRepository.getTouristPlace()
    }

    val touristPlaces: LiveData<Array<TouristPlace>> = _touristPlaces
}
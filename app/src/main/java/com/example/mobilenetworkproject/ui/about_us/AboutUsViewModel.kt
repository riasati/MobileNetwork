package com.example.mobilenetworkproject.ui.about_us

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilenetworkproject.model.entity.StudentModel
import com.example.mobilenetworkproject.model.repository.HomeDataRepository

class AboutUsViewModel : ViewModel() {

    private val _studentModel = MutableLiveData<Array<StudentModel>>().apply {
        value = HomeDataRepository.getStudents()
    }
    val studentModel: LiveData<Array<StudentModel>> = _studentModel
}
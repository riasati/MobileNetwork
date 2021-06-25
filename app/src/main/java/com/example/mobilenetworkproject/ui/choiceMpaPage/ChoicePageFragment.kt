package com.example.mobilenetworkproject.ui.choiceMpaPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mobilenetworkproject.R

class ChoicePageFragment : Fragment() {

    private lateinit var aboutUsViewModel: ChoicePageViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutUsViewModel =
                ViewModelProviders.of(this).get(ChoicePageViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }
}
package com.example.mobilenetworkproject.ui.mapPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mobilenetworkproject.R

class MapPageFragment : Fragment() {

    private lateinit var touristPlacePageViewModel: MapPageViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        touristPlacePageViewModel =
                ViewModelProviders.of(this).get(MapPageViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }
}
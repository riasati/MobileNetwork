package com.example.mobilenetworkproject.ui.slideshow

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

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.tourist_place_detail_card, container, false)
        val imageView: ImageView = root.findViewById(R.id.tourist_place_image)
        val textView_name: TextView = root.findViewById(R.id.tourist_place_name)
        val textView_country: TextView = root.findViewById(R.id.tourist_place_country)
        val textView_star: TextView = root.findViewById(R.id.tourist_place_star)
        val textView_description: TextView = root.findViewById(R.id.tourist_place_description)
        slideshowViewModel.touristPlacesModel.observe(viewLifecycleOwner, Observer {
            imageView.setImageResource(it[1].imageResourceId)
            textView_name.text = it[1].name
            textView_country.text = it[1].country
            textView_star.text = it[1].star.toString()
            textView_description.text = it[1].description
        })
        return root
    }
}
package com.example.mobilenetworkproject.ui.about_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mobilenetworkproject.R

class AboutUsFragment : Fragment() {

    private lateinit var aboutUsViewModel: AboutUsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        aboutUsViewModel =
                ViewModelProviders.of(this).get(AboutUsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_about_us, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        aboutUsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
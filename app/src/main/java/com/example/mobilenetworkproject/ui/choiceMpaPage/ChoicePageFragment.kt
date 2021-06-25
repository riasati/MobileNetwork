package com.example.mobilenetworkproject.ui.choiceMpaPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import android.widget.TextView
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
        val lacButton: Button = root.findViewById(R.id.lac_button)
        lacButton.setOnClickListener {

        }
        val cellButton: Button = root.findViewById(R.id.cell_button)
        cellButton.setOnClickListener {

        }
        val plmnButton: Button = root.findViewById(R.id.plmn_button)
        plmnButton.setOnClickListener {

        }
        val tecButton: Button = root.findViewById(R.id.tec_button)
        tecButton.setOnClickListener {

        }
        return root
    }
}
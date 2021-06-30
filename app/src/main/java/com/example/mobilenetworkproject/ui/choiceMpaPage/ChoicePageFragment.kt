package com.example.mobilenetworkproject.ui.choiceMpaPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.ui.mapPage.MapActivity

class ChoicePageFragment : Fragment() {

    private lateinit var choiceMapViewModel: ChoicePageViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        choiceMapViewModel =
            ViewModelProviders.of(this).get(ChoicePageViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_choice_map, container, false)
        Thread{
            this.getDataFromDB()
        }.start()
        val lacButton: Button = root.findViewById(R.id.lac_button)
        lacButton.setOnClickListener {
            MapActivity.mapChoice = "LAC"
            this.findNavController().navigate(R.id.action_choice_map_to_map_page)
        }
        val cellButton: Button = root.findViewById(R.id.cell_button)
        cellButton.setOnClickListener {
            MapActivity.mapChoice = "CELL"
            this.findNavController().navigate(R.id.action_choice_map_to_map_page)
        }
        val plmnButton: Button = root.findViewById(R.id.plmn_button)
        plmnButton.setOnClickListener {
            MapActivity.mapChoice = "PLMN"
            this.findNavController().navigate(R.id.action_choice_map_to_map_page)
        }
        val tecButton: Button = root.findViewById(R.id.tec_button)
        tecButton.setOnClickListener {
            MapActivity.mapChoice = "TEC"
            this.findNavController().navigate(R.id.action_choice_map_to_map_page)
        }
        return root
    }

    private fun getDataFromDB() {
        MapActivity.locationsInformation = arrayListOf()
        MapActivity.cellsInformation = mutableMapOf()
        MapActivity.locationsInformation = choiceMapViewModel.selectAllLocationInformation()!!
        choiceMapViewModel.selectAllCellsInformation()
            ?.map { cellInformation ->
                MapActivity.cellsInformation.put(
                    cellInformation.cellId,
                    cellInformation
                )
            }
        MapActivity.dataIsReady = true
    }
}
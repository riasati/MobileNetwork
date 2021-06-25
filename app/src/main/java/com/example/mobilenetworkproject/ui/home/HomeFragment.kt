package com.example.mobilenetworkproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.model.domain.CellInformation
import org.json.JSONObject
import com.example.mobilenetworkproject.model.data.repository.impl.HomeDataRepositoryImpl.insertCellInformation
import com.example.mobilenetworkproject.model.data.repository.impl.HomeDataRepositoryImpl.insertLocationInformation
import com.example.mobilenetworkproject.model.domain.LocationInformation


interface RecyclerViewClickListener {
    fun recyclerViewListClicked(v: View?, position: Int)
}
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        root.findViewById<Button>()
//        val button = findViewByID<Button>(R.id.startButton)
//        button.setOnClickListener(View.OnClickListener {
//            button.text = "You just clicked me"
//        })

        return root
    }
        fun onStartButtonClick(v: View){
        val mToast = Toast.makeText(v.context,"button 3 clicked", Toast.LENGTH_SHORT)
        mToast.show()
//        button.text = "You just clicked me"
    }


}
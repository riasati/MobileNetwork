package com.example.mobilenetworkproject.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.telephony.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.model.data.repository.impl.HomeDataRepositoryImpl
import com.example.mobilenetworkproject.model.domain.CellInformation
import org.json.JSONObject
import com.example.mobilenetworkproject.model.data.repository.impl.HomeDataRepositoryImpl.insertCellInformation
import com.example.mobilenetworkproject.model.data.repository.impl.HomeDataRepositoryImpl.insertLocationInformation
import com.example.mobilenetworkproject.model.domain.LocationInformation
import com.google.android.material.snackbar.Snackbar


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
        val button = root.findViewById<Button>(R.id.startButton)
        button.setOnClickListener(View.OnClickListener {
            if (button.text == "Start"){
                button.text = "Finished"
                getLocation(root,root.context,homeViewModel)
            }
            else if (button.text == "Finished"){
                button.text = "Start"
                Snackbar.make(root.context,root,"Click",Snackbar.LENGTH_SHORT).show()
            }

            //Toast.makeText(root.context,"button 3 clicked", Toast.LENGTH_SHORT)
        })

        return root
    }
    fun AddDataToRepository(cellObj: JSONObject?, locationObj: JSONObject?,sendCellToReposity : Boolean,homeViewModel: HomeViewModel): Void? {
        if (cellObj == null || locationObj == null) {
            return null
        }
//        cellObj.getString("cellPLMN"),
//        cellObj.getInt("cellARFCN"),
//        cellObj.getInt("cellCode")
        if (sendCellToReposity){
            val cellInfo = CellInformation(
                cellObj.getString("cellGeneration"),
                cellObj.getLong("cellId"),
                cellObj.getString("cellPLMN"),
                cellObj.getInt("cellARFCN"),
                cellObj.getInt("cellLac"),
                cellObj.getInt("cellCode")
            )
            homeViewModel.insertCellInformation(cellInfo)
        }
        val LocationInfo = LocationInformation(
            cellObj.getLong("cellId"),
            locationObj.getDouble("longitude"),
            locationObj.getDouble("latitude")
        )
        homeViewModel.insertLocationInformation(LocationInfo)
        return null
    }

    @SuppressLint("MissingPermission")
    fun getActiveCellInfo(context: Context): CellInfo? {
        val tel = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var numRegisteredCellInfo = 0
        val cellInfos = tel.allCellInfo ?: return null
        var result: CellInfo? = null
        for (i in cellInfos.indices) {
            val cellInfo = cellInfos[i]
            if (cellInfo.isRegistered) {
                numRegisteredCellInfo++
                if (numRegisteredCellInfo > 1) {
                    return null
                }
                result = cellInfo
            }
        }
        return result
    }

    //@RequiresApi(Build.VERSION_CODES.Q)
    fun getCellInformation(cellInfo: CellInfo?): JSONObject? {
        val cellObj = JSONObject()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
            if (cellInfo is CellInfoNr) {
                val NrCellIdentity: CellIdentityNr = cellInfo.cellIdentity as CellIdentityNr
                cellObj.put("cellGeneration", "NR")
                cellObj.put("cellId", NrCellIdentity.nci)
                cellObj.put("cellPLMN", NrCellIdentity.mccString + NrCellIdentity.mncString)
                cellObj.put("cellARFCN", NrCellIdentity.nrarfcn)
                cellObj.put("cellLac", NrCellIdentity.tac)
                cellObj.put("cellCode", NrCellIdentity.pci)
                return cellObj
            }
        }
        if (cellInfo is CellInfoGsm) {
            val GsmCellIdentity = cellInfo.cellIdentity
            cellObj.put("cellGeneration", "GSM")
            cellObj.put("cellId", GsmCellIdentity.cid)
            cellObj.put("cellLac", GsmCellIdentity.lac)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
                cellObj.put("cellPLMN", GsmCellIdentity.mobileNetworkOperator)
                cellObj.put("cellARFCN", GsmCellIdentity.arfcn)
                cellObj.put("cellCode", GsmCellIdentity.bsic)
            }
            else{
                cellObj.put("cellPLMN", "null")
                cellObj.put("cellARFCN", 0)
                cellObj.put("cellCode", 0)
            }
            return cellObj
        }
        if (cellInfo is CellInfoLte) {
            val LteCellIdentity = cellInfo.cellIdentity
            cellObj.put("cellGeneration", "LTE")
            cellObj.put("cellId", LteCellIdentity.ci)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
                cellObj.put("cellPLMN", LteCellIdentity.mobileNetworkOperator)
                cellObj.put("cellARFCN", LteCellIdentity.earfcn)
            }
            else{
                cellObj.put("cellPLMN", "null")
                cellObj.put("cellARFCN", 0)
            }
            cellObj.put("cellLac", LteCellIdentity.tac)
            cellObj.put("cellCode", LteCellIdentity.pci)
            return cellObj
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
            if (cellInfo is CellInfoWcdma) {
                val WcdmaCell = cellInfo as CellInfoWcdma
                val WcdmaCellIdentity = WcdmaCell.cellIdentity
                cellObj.put("cellGeneration", "WCDMA")
                cellObj.put("cellId", WcdmaCellIdentity.cid)
                cellObj.put("cellPLMN", WcdmaCellIdentity.mobileNetworkOperator)
                cellObj.put("cellARFCN", WcdmaCellIdentity.uarfcn)
                cellObj.put("cellLac", WcdmaCellIdentity.lac)
                cellObj.put("cellCode", WcdmaCellIdentity.psc)
                return cellObj
            }
        }
        return null
    }

    //    @SuppressLint("ServiceCast")
    @SuppressLint("MissingPermission","ServiceCast")
    fun getLocation(root: View,context: Context,homeViewModel: HomeViewModel){

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0F, object :
            LocationListener {
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onLocationChanged(locationGps: Location) {
                val locationObj = JSONObject()
                if (locationGps != null) {
                    localGpsLocation = locationGps
                    locationObj.put("longitude", localGpsLocation!!.longitude)
                    locationObj.put("latitude", localGpsLocation!!.latitude)

                    val cellInfoObject : CellInfo? = getActiveCellInfo(context)
                    val cellJsonObject : JSONObject? = getCellInformation(cellInfoObject)
                    Snackbar.make(root.context,root,"NewRecordFalse",Snackbar.LENGTH_SHORT).show()
                    Thread {
                        val checkExistanceOfCell : CellInformation? = homeViewModel.getCelInformationByCellId(cellJsonObject!!.getLong("cellId"))
                        if (checkExistanceOfCell == null){
                            AddDataToRepository(cellJsonObject,locationObj,true,homeViewModel)
                            print("HERE HERE HERE HERE HERE HERE HERE")
                            Snackbar.make(root.context,root,cellJsonObject.get("cellId").toString(),Snackbar.LENGTH_SHORT).show()
                        }
                        else{
                            AddDataToRepository(cellJsonObject,locationObj,false,homeViewModel)
                            Snackbar.make(root.context,root,"NewRecordFalse",Snackbar.LENGTH_SHORT).show()
                        }
                    }.start()


                    //curruntLocation.accuracy
                    //curruntLocation!!.longitude
                    //curruntLocation!!.latitude
                    //Snackbar.make(context,view,"sadfads",Snackbar.LENGTH_SHORT).show()
                }
            }
            var localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

//            override fun onProviderEnabled(p0: String?) {}
//
//            override fun onProviderDisabled(p0: String?) {}
        })
        var localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }
}
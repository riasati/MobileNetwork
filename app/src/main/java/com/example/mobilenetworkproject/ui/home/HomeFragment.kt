package com.example.mobilenetworkproject.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.telephony.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.domain.LocationInformation
import com.google.android.gms.location.*
import org.json.JSONObject


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
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
            if (button.text == "Start") {
                button.text = "Finish"
                button.setBackgroundResource(R.color.number10)
                Toast.makeText(root.context, "Started", Toast.LENGTH_SHORT).show()
                getLocation(root, root.context, homeViewModel)
                startLocationUpdates()
            } else if (button.text == "Finish") {
                stopLocationUpdates()
                Toast.makeText(root.context, "Finished", Toast.LENGTH_SHORT).show()
                this.findNavController().navigate(R.id.action_nav_home_to_choice_map)
            }
        })

        return root
    }
    fun addDataToRepository(
        cellObj: JSONObject?,
        locationObj: JSONObject?,
        sendCellToRepository: Boolean,
        homeViewModel: HomeViewModel
    ): Void? {
        if (cellObj == null || locationObj == null) {
            return null
        }
        if (sendCellToRepository){
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
        val locationInfo = LocationInformation(
            cellObj.getLong("cellId"),
            locationObj.getDouble("longitude"),
            locationObj.getDouble("latitude")
        )
        homeViewModel.insertLocationInformation(locationInfo)
        return null
    }

    fun getActiveCellInfo(context: Context): CellInfo? {
        val tel = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var numRegisteredCellInfo = 0
       if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
           return null
       }
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
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                @Suppress("DEPRECATION")
                cellObj.put(
                    "cellPLMN",
                    GsmCellIdentity.mcc.toString() + GsmCellIdentity.mnc.toString()
                )
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    cellObj.put("cellARFCN", GsmCellIdentity.arfcn)
                    cellObj.put("cellCode", GsmCellIdentity.bsic)
                }
                else{
                    cellObj.put("cellARFCN", 0)
                    cellObj.put("cellCode", 0)
                }
            }
            else{
                cellObj.put("cellPLMN", "43211")
                cellObj.put("cellARFCN", 0)
                cellObj.put("cellCode", 0)
            }
            return cellObj
        }
        if (cellInfo is CellInfoLte) {
            val LteCellIdentity = cellInfo.cellIdentity
            cellObj.put("cellGeneration", "LTE")
            cellObj.put("cellId", LteCellIdentity.ci)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                @Suppress("DEPRECATION")
                cellObj.put(
                    "cellPLMN",
                    LteCellIdentity.mcc.toString() + LteCellIdentity.mnc.toString()
                )
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    cellObj.put("cellARFCN", LteCellIdentity.earfcn)
                }
                else{
                    cellObj.put("cellARFCN", 0)
                }
            }
            else{
                cellObj.put("cellPLMN", "43211")
                cellObj.put("cellARFCN", 0)
            }
            cellObj.put("cellLac", LteCellIdentity.tac)
            cellObj.put("cellCode", LteCellIdentity.pci)
            return cellObj
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            if (cellInfo is CellInfoWcdma) {
                val WcdmaCell = cellInfo as CellInfoWcdma
                val WcdmaCellIdentity = WcdmaCell.cellIdentity
                cellObj.put("cellGeneration", "WCDMA")
                cellObj.put("cellId", WcdmaCellIdentity.cid)
                @Suppress("DEPRECATION")
                cellObj.put(
                    "cellPLMN",
                    WcdmaCellIdentity.mcc.toString() + WcdmaCellIdentity.mnc.toString()
                )
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    cellObj.put("cellARFCN", WcdmaCellIdentity.uarfcn)
                }
                else{
                    cellObj.put("cellARFCN", 0)
                }
                cellObj.put("cellLac", WcdmaCellIdentity.lac)
                cellObj.put("cellCode", WcdmaCellIdentity.psc)
                return cellObj
            }
        }
        return null
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    @SuppressLint("MissingPermission")
    fun getLocation(root: View, context: Context, homeViewModel: HomeViewModel){

        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var localGpsLocation : Location?
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                localGpsLocation = location
            }
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            smallestDisplacement = 10F
        }
        locationCallback = object : LocationCallback() {
            val locationObj = JSONObject()
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                localGpsLocation = locationResult.lastLocation
                locationObj.put("longitude", localGpsLocation!!.longitude)
                locationObj.put("latitude", localGpsLocation!!.latitude)

                val cellInfoObject : CellInfo? = getActiveCellInfo(context)
                val cellJsonObject : JSONObject? = getCellInformation(cellInfoObject)

                Thread {
                    if (cellJsonObject != null){
                        val checkExistanceOfCell : CellInformation? = homeViewModel.getCelInformationByCellId(
                            cellJsonObject.getLong(
                                "cellId"
                            )
                        )
                        if (checkExistanceOfCell == null){
                            addDataToRepository(cellJsonObject, locationObj, true, homeViewModel)
                            activity!!.runOnUiThread {
                                Toast.makeText(activity, "add cell with id " + cellJsonObject.getString("cellId"), Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            addDataToRepository(cellJsonObject, locationObj, false, homeViewModel)
                            activity!!.runOnUiThread {
                                Toast.makeText(activity, "add new location ", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
//                    activity!!.runOnUiThread {
//                        Toast.makeText(activity, "add new location " + locationObj.get("longitude").toString(), Toast.LENGTH_SHORT).show()
//                    }
                }.start()
            }
        }

    }
}
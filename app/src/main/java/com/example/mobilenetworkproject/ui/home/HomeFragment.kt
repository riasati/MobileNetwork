package com.example.mobilenetworkproject.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.TrafficStats
import android.os.Build
import android.os.Bundle
import android.telephony.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.model.domain.LocationInformation
import com.google.android.gms.location.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.*
//import org.apache.http.params.HttpParams
//import org.apache.http.*
//import org.apache.http.params.HttpConnectionParams
import java.net.HttpURLConnection
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.math.absoluteValue


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
                this.findNavController().navigate(R.id.map)
            }
        })

        return root
    }
    fun addDataToRepository(
        informationObj: JSONObject?,
        cellInfoObj: JSONObject?,
        homeViewModel: HomeViewModel
    ): Void? {
        if (informationObj == null || cellInfoObj == null) {
            return null
        }
        val locationInfo = LocationInformation(
            cellInfoObj.getLong("cellId"),
            informationObj.getDouble("LocationLongitude"),
            informationObj.getDouble("LocationLatitude"),
            cellInfoObj.getString("cellPLMN"),
            informationObj.getString("ping"),
            informationObj.getString("jitter"),
            informationObj.getString("downLinkRate"),
            informationObj.getString("upLinkRate"),
            cellInfoObj.getString("RssiRXlev"),
            cellInfoObj.getString("RsrpRsrq"),
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
                val NrCellSignalStrength: CellSignalStrengthNr = cellInfo.cellSignalStrength as CellSignalStrengthNr
                cellObj.put("cellId", NrCellIdentity.nci)
                cellObj.put("cellPLMN", NrCellIdentity.mccString + NrCellIdentity.mncString)
                cellObj.put("RssiRXlev", NrCellSignalStrength.dbm.toString())
                cellObj.put("RsrpRsrq", NrCellSignalStrength.ssRsrq.toString())
                return cellObj
            }
        }
        if (cellInfo is CellInfoGsm) {
            val GsmCellIdentity = cellInfo.cellIdentity
            val GsmCellSignalStrength: CellSignalStrengthGsm = cellInfo.cellSignalStrength as CellSignalStrengthGsm
            cellObj.put("cellId", GsmCellIdentity.cid)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                @Suppress("DEPRECATION")
                cellObj.put(
                    "cellPLMN",
                    GsmCellIdentity.mcc.toString() + GsmCellIdentity.mnc.toString()
                )
            }
            else{
                cellObj.put("cellPLMN", "43211")
            }
            cellObj.put("RssiRXlev", GsmCellSignalStrength.dbm.toString())
            cellObj.put("RsrpRsrq", "null")
            return cellObj
        }
        if (cellInfo is CellInfoLte) {
            val LteCellIdentity = cellInfo.cellIdentity
            val LteCellSignalStrength: CellSignalStrengthLte = cellInfo.cellSignalStrength as CellSignalStrengthLte
            cellObj.put("cellId", LteCellIdentity.ci)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                @Suppress("DEPRECATION")
                cellObj.put(
                    "cellPLMN",
                    LteCellIdentity.mcc.toString() + LteCellIdentity.mnc.toString()
                )
            }
            else{
                cellObj.put("cellPLMN", "43211")
            }
            cellObj.put("RssiRXlev", LteCellSignalStrength.dbm.toString())
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                cellObj.put("RsrpRsrq", LteCellSignalStrength.rsrq.toString())
            }
            else{
                cellObj.put("RsrpRsrq", "null")
            }
            return cellObj
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            if (cellInfo is CellInfoWcdma) {
                val WcdmaCellIdentity = cellInfo.cellIdentity
                val WcdmaCellSignalStrength: CellSignalStrengthWcdma = cellInfo.cellSignalStrength as CellSignalStrengthWcdma
                cellObj.put("cellId", WcdmaCellIdentity.cid)
                @Suppress("DEPRECATION")
                cellObj.put(
                    "cellPLMN",
                    WcdmaCellIdentity.mcc.toString() + WcdmaCellIdentity.mnc.toString()
                )
                cellObj.put("RssiRXlev", WcdmaCellSignalStrength.dbm.toString())
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    cellObj.put("RsrpRsrq", WcdmaCellSignalStrength.ecNo.toString())
                }
                else{
                    cellObj.put("RsrpRsrq", "null")
                }
                return cellObj
            }
        }
        return null
    }

    fun getPingAndJitter():Array<String>{
        var ping = ""
        var jitter = ""

        val ip = "google.com"
        val pingCmd = "ping -c 10 $ip"

        val r = Runtime.getRuntime()
        val p = r.exec(pingCmd)
        val `in` = BufferedReader(InputStreamReader(p.inputStream))

        val lineList: ArrayList<String> = ArrayList()
        val pingTimeList: ArrayList<Int> = ArrayList()
        val jitterTimeList: ArrayList<Int> = ArrayList()

        var inputLine: String?
        while (`in`.readLine().also { inputLine = it } != null) {
            lineList.add(inputLine.toString())
        }

        //get ping average
        val lastLine = lineList[lineList.lastIndex]
        val keyValue = lastLine.split("=".toRegex()).toTypedArray()
        val value = keyValue[1].split("/".toRegex()).toTypedArray()
        ping = value[1]

        //get times
        lineList.forEachIndexed { index, s ->
            if (index >= 1 && index <= lineList.size-5){
                val splitedLine = lineList[index].split(" ".toRegex()).toTypedArray()
                val splitedKeyValue = splitedLine[splitedLine.lastIndex - 1].split("[=<]".toRegex()).toTypedArray()
                pingTimeList.add(splitedKeyValue[1].toInt())
            }
        }

        //get difference of times
        var previousTime = -1
        pingTimeList.forEachIndexed { index, i ->
            if(previousTime == -1) {
                previousTime = i
            }
            else{
                if ((i - previousTime).absoluteValue != 0){
                    jitterTimeList.add((i - previousTime).absoluteValue)
                }
            }
        }

        //calculate jitter
        jitter = (((jitterTimeList.sum() + 0.0)/jitterTimeList.size).toString())

        return arrayOf<String>(ping, jitter)
    }

   // @RequiresApi(Build.VERSION_CODES.N)
    fun getDownAndUpLinkRate():Array<String>{
        var downLinkRate = ""
        var upLinkRate = ""

        val beforeTime = System.currentTimeMillis()
        val totalTxBeforeTest = TrafficStats.getTotalTxBytes()
        val totalRxBeforeTest = TrafficStats.getTotalRxBytes()


        val url = "http://www.google.com/"

        val client = OkHttpClient()
       // var url2 = HttpUrl("http://www.google.com/")

//        val JSON = MediaType.get("application/json; charset=utf-8")
//        val body = RequestBody.create(JSON, "{\"data\":\"$data\"}")
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = client.newCall(request).execute()

        println(response.request())
        println(response.body()!!.string())

//        String host = YOUR_HOST
//                OkHttpGet request = new HttpGet(host);
//        HttpParams httpParameters = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
//        HttpClient httpClient = new DefaultHttpClient(httpParameters);
//
//        for(int i=0; i<5; i++) {
//            long BeforeTime = System.currentTimeMillis();
//            HttpResponse response = httpClient.execute(request);
//            long AfterTime = System.currentTimeMillis();
//            Long TimeDifference = AfterTime - BeforeTime;
//            time[i] = TimeDifference
//        }


    //    val url = URL("http://www.google.com/")

     //   var a = URL("http://google.com").openConnection()

  //      var result = ""

//        with(url.openConnection() as HttpURLConnection) {
//            requestMethod = "GET"  // optional default is GET
//
//            println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")
//
//            inputStream.bufferedReader().use {
//                it.lines().forEach { line ->
//                    result += line + "\n"
////                    println(line)
//                }
//            }
//        }

        val totalTxAfterTest = TrafficStats.getTotalTxBytes()
        val totalRxAfterTest = TrafficStats.getTotalRxBytes()
        val afterTime = System.currentTimeMillis()

        val timeDifference = (afterTime - beforeTime).toDouble()

        val rxDiff = (totalRxAfterTest - totalRxBeforeTest).toDouble()
        val txDiff = (totalTxAfterTest - totalTxBeforeTest).toDouble()

        if (rxDiff != 0.0 && txDiff != 0.0) {
            val rxBPS = rxDiff / (timeDifference / 1000) // total rx bytes per second.
            val txBPS = txDiff / (timeDifference / 1000) // total tx bytes per second.

            downLinkRate = "$rxBPS B/s. Total download = $rxDiff"
            upLinkRate = "$txBPS B/s. Total upload = $txDiff"
        }

        return arrayOf<String>(downLinkRate, upLinkRate)
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
            smallestDisplacement = 0F
        }
        locationCallback = object : LocationCallback() {
            val informationObj = JSONObject()
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                localGpsLocation = locationResult.lastLocation
                informationObj.put("LocationLongitude", localGpsLocation!!.longitude)
                informationObj.put("LocationLatitude", localGpsLocation!!.latitude)

                activity!!.runOnUiThread {
                    Toast.makeText(activity, "getting cell information", Toast.LENGTH_SHORT).show()
                }
                val cellInfoObject : CellInfo? = getActiveCellInfo(context)
                val cellJsonObject : JSONObject? = getCellInformation(cellInfoObject)

                activity!!.runOnUiThread {
                    Toast.makeText(activity, "getting ping and jitter information", Toast.LENGTH_SHORT).show()
                }

                val pingAndJitter = getPingAndJitter()
                informationObj.put("ping", pingAndJitter[0])
                informationObj.put("jitter", pingAndJitter[1])


                activity!!.runOnUiThread {
                    Toast.makeText(activity, "getting download and upload information", Toast.LENGTH_SHORT).show()
                }

                //this function has bugs
                val downAndUpLinkRate = getDownAndUpLinkRate()
                informationObj.put("downLinkRate", downAndUpLinkRate[0])
                informationObj.put("upLinkRate", downAndUpLinkRate[1])


                Thread {
                    addDataToRepository(informationObj,cellJsonObject,homeViewModel)
                }.start()
            }
        }

    }
}
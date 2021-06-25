package com.example.mobilenetworkproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mobilenetworkproject.model.data.repository.impl.HomeDataRepositoryImpl
import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.domain.LocationInformation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.telephony.*
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        val button : Button = findViewById(R.id.startButton)
        button.setOnClickListener(){
            button.text = "You just clicked me"
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_about_us), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
//    fun onStartButtonClick(v: View){
////        val mToast = Toast.makeText(applicationContext,"button 3 clicked", Toast.LENGTH_SHORT)
////        mToast.show()
////        button.text = "You just clicked me"
//    }
fun AddDataToRepository(cellObj: JSONObject, locationObj: JSONObject): Void? {
    val cellInfo = CellInformation(
        cellObj.getString("cellGeneration"),
        cellObj.getLong("cellId"),
        cellObj.getString("cellPLMN"),
        cellObj.getInt("cellARFCN"),
        cellObj.getInt("cellLac"),
        cellObj.getInt("cellCode")
    )
    HomeDataRepositoryImpl.insertCellInformation(cellInfo)
    val LocationInfo = LocationInformation(
        cellObj.getLong("cellId"),
        locationObj.getDouble("LocationLongitude"),
        locationObj.getDouble("LocationLatitude")
    )
    HomeDataRepositoryImpl.insertLocationInformation(LocationInfo)
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

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getSignalStrengthDbm(cellInfo: CellInfo?): JSONObject? {
        val cellObj = JSONObject()
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
        if (cellInfo is CellInfoGsm) {
            val GsmCellIdentity = cellInfo.cellIdentity
            cellObj.put("cellGeneration", "GSM")
            cellObj.put("cellId", GsmCellIdentity.cid)
            cellObj.put("cellPLMN", GsmCellIdentity.mobileNetworkOperator)
            cellObj.put("cellARFCN", GsmCellIdentity.arfcn)
            cellObj.put("cellLac", GsmCellIdentity.lac)
            cellObj.put("cellCode", GsmCellIdentity.bsic)
            return cellObj
        }
        if (cellInfo is CellInfoLte) {
            val LteCellIdentity = cellInfo.cellIdentity
            cellObj.put("cellGeneration", "LTE")
            cellObj.put("cellId", LteCellIdentity.ci)
            cellObj.put("cellPLMN", LteCellIdentity.mobileNetworkOperator)
            cellObj.put("cellARFCN", LteCellIdentity.earfcn)
            cellObj.put("cellLac", LteCellIdentity.tac)
            cellObj.put("cellCode", LteCellIdentity.pci)
            return cellObj
        }
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
        return cellObj
    }

//    @SuppressLint("ServiceCast")
    @SuppressLint("MissingPermission","ServiceCast")
    fun getLocation(context: Context){
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        var location: Location? = null
        if (hasGps || hasNetwork){
            if (hasGps){
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object :
//                    LocationListener {
//                    override fun onLocationChanged(p0: Location) {
//                        if (p0 != null) {
//                            locationGps = p0
//                            if (uid != null) {
//                                Firebase.firestore.collection("Drivers").document(uid).update("Longitude",
//                                    locationGps!!.longitude,"Latitude", locationGps!!.latitude)
//                                    .addOnSuccessListener {
//                                        Snackbar.make(takeabreak, "Location Data feeds start", Snackbar.LENGTH_SHORT).show()
//                                    }
//                                    .addOnFailureListener {
//                                        Snackbar.make(takeabreak, "Failed location feed", Snackbar.LENGTH_SHORT).show()
//                                    }
//                            }
//                        }
//                    }
//                })
            }
            if (hasNetwork){

            }
            if(locationGps!= null && locationNetwork!= null){

            }
        }
        else{
//            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
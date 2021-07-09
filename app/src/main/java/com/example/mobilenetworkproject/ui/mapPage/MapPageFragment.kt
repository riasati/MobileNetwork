package com.example.mobilenetworkproject.ui.mapPage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.model.domain.LocationInformation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapPageViewModel: MapPageViewModel
    private lateinit var myMap: GoogleMap

    private var locationsInformation = listOf<LocationInformation>()
    private var dataIsReady = false
    private var cellColors = mutableMapOf<Long, Int>()
    private val POLYLINE_STROKE_WIDTH_PX = 12
    private val systemColors = arrayListOf<Int>(
        R.color.number1,
        R.color.number2,
        R.color.number3,
        R.color.number4,
        R.color.number5,
        R.color.number6,
        R.color.number7,
        R.color.number8,
        R.color.number9,
        R.color.number10,
        R.color.number11,
        R.color.number12,
        R.color.number13,
        R.color.number14,
        R.color.number15,
        R.color.number16,
        R.color.number17,
        R.color.number18,
        R.color.number19,
        R.color.number20,
        R.color.number21,
        R.color.number22,
        R.color.number23,
        R.color.number24,
        R.color.number25,
        R.color.number26,
        R.color.number27,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapPageViewModel = ViewModelProviders.of(this).get(MapPageViewModel::class.java)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap
        // Add custom info window
        myMap.setInfoWindowAdapter(CustomInfoWindowForGoogleMap(this))
        // wait until data is ready
        Thread {
            this.getDataFromDB()
        }.start()
        while (!dataIsReady) {
            continue
        }
        dataIsReady = false
        // Add information to map
        this.addInformationToMap()

    }

    private fun addInformationToMap() {
        if (locationsInformation.isEmpty()) {
            return
        }
        var currentLocationInformation: LocationInformation
        var nextLocationInformation: LocationInformation
        for (index: Int in 0 until locationsInformation.size - 1) {
            currentLocationInformation = locationsInformation[index]
            nextLocationInformation = locationsInformation[index + 1]
            this.addLocationsInformationToMap(currentLocationInformation)
            this.addPolylineToMap(
                currentLocationInformation = currentLocationInformation,
                nextLocationInformation = nextLocationInformation
            )

        }
        val lastLocationInformation = locationsInformation[locationsInformation.size - 1]
        this.addLocationsInformationToMap(lastLocationInformation)
        myMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    lastLocationInformation.LocationLatitude,
                    lastLocationInformation.LocationLongitude
                ), 6f
            )
        )
    }

    private fun addLocationsInformationToMap(
        locationInformation: LocationInformation
    ) {
        myMap.addMarker(
            MarkerOptions()
                .position(
                    LatLng(
                        locationInformation.LocationLatitude,
                        locationInformation.LocationLongitude
                    )
                )
                .title("CELL_ID: " + locationInformation.cellId.toString())
                .snippet(
                    "PING: " + locationInformation.ping + "     " + "CELL_PLMN: " + locationInformation.cellPLMN + "\n" +
                            "JITTER: " + locationInformation.jitter + "     " + "DOWN_LINK_RATE: " + locationInformation.downLinkRate + "\n" +
                            "UP_LINK_RATE: " + locationInformation.upLinkRate + "     " + "RSSI_RXLEV: " + locationInformation.RssiRXlev + "\n" +
                            "RSRP_RSRQ: " + locationInformation.RsrpRsrq
                )
        )
    }

    private fun addPolylineToMap(
        currentLocationInformation: LocationInformation,
        nextLocationInformation: LocationInformation
    ) {
        val polyline = myMap.addPolyline(
            PolylineOptions()
                .clickable(false)
                .add(
                    LatLng(
                        currentLocationInformation.LocationLatitude,
                        currentLocationInformation.LocationLongitude
                    ),
                    LatLng(
                        nextLocationInformation.LocationLatitude,
                        nextLocationInformation.LocationLongitude
                    ),
                )
        )
        this.stylePolyline(polyline, currentLocationInformation)
    }

    private fun stylePolyline(polyline: Polyline, currentLocationInformation: LocationInformation) {
        polyline.endCap = RoundCap()
        polyline.width = POLYLINE_STROKE_WIDTH_PX.toFloat()
        val color = this.getSuitableColor(currentLocationInformation) ?: R.color.colorBlack
        polyline.color = color
        polyline.jointType = JointType.ROUND
    }

    private fun getSuitableColor(currentLocationInformation: LocationInformation): Int? {
        if (!cellColors.containsKey(currentLocationInformation.cellId)) {
            cellColors.put(
                currentLocationInformation.cellId,
                systemColors[(cellColors.size + 1) % 27]
            )
        }
        return cellColors[currentLocationInformation.cellId]
    }

    private fun getDataFromDB() {
        this.locationsInformation = arrayListOf()
        this.locationsInformation = this.mapPageViewModel.selectAllLocationInformation()!!
        this.dataIsReady = true
    }
}

class CustomInfoWindowForGoogleMap(context: Context) : GoogleMap.InfoWindowAdapter {

    var mContext = context

    @SuppressLint("InflateParams")
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.fragment_info_window, null)

    private fun rendowWindowText(marker: Marker, view: View) {

        val windowTitle = view.findViewById<TextView>(R.id.title)
        val windowTextView1 = view.findViewById<TextView>(R.id.snippet)

        windowTitle.text = marker.title
        windowTextView1.text = marker.snippet

    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
//        rendowWindowText(marker, mWindow)
//        return mWindow
        return null
    }
}
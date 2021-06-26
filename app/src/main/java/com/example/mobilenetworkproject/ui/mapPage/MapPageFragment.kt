package com.example.mobilenetworkproject.ui.mapPage

import android.annotation.SuppressLint
import android.app.Activity
import android.app.WallpaperColors
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mobilenetworkproject.R
import com.example.mobilenetworkproject.model.domain.CellInformation
import com.example.mobilenetworkproject.model.domain.LocationInformation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapPageViewModel: MapPageViewModel
    private lateinit var myMap: GoogleMap
    private lateinit var mapChoice: String
    private lateinit var cellColors: Map<Long, Int>
    private lateinit var lacColors: Map<Int, Int>
    private lateinit var tecColors: Map<String, Int>
    private lateinit var plmnColors: Map<String, Int>
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
        mapChoice = savedInstanceState?.getString("CHOICE").toString()
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
        // Add information to map
        this.addInformationToMap()

    }

    private fun addInformationToMap() {
        val locationsInformation = mapPageViewModel.selectAllLocationInformation()?:return
        if (locationsInformation.isEmpty()) {
            return
        }
        var lastCellId = -1L
        var currentLocationInformation: LocationInformation
        var nextLocationInformation: LocationInformation
        for (index: Int in 0 until locationsInformation.size - 1) {
            currentLocationInformation = locationsInformation[index]
            if (currentLocationInformation.cellId != lastCellId) {
                this.addCellsInformationToMap(
                    currentLocationInformation.LocationLatitude,
                    currentLocationInformation.LocationLongitude,
                    currentLocationInformation.cellId
                )
                lastCellId = currentLocationInformation.cellId
            }
            nextLocationInformation = locationsInformation[index + 1]
            this.addPolylineToMap(
                currentLocationInformation = currentLocationInformation,
                nextLocationInformation = nextLocationInformation
            )

        }
        val lastLocationInformation = locationsInformation[locationsInformation.size - 1]
        if (lastLocationInformation.cellId != lastCellId) {
            this.addCellsInformationToMap(
                lastLocationInformation.LocationLatitude,
                lastLocationInformation.LocationLongitude,
                lastLocationInformation.cellId
            )
        }
        myMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    lastLocationInformation.LocationLatitude,
                    lastLocationInformation.LocationLongitude
                ), 4f
            )
        )
    }

    private fun addCellsInformationToMap(
        cellLatitude: Double,
        cellLongitude: Double,
        cellId: Long
    ) {
        // TODO ADD CUSTOM INFORMATION
        val cellInformation = mapPageViewModel.getCellInformationByCellId(cellId)?:return
        myMap.addMarker(
            MarkerOptions()
                .position(LatLng(cellLatitude, cellLongitude))
                .title("title")
                .snippet("snippet")
        )
    }

    private fun addPolylineToMap(
        currentLocationInformation: LocationInformation,
        nextLocationInformation: LocationInformation
    ) {
        val polyline = myMap.addPolyline(
            PolylineOptions()
                .clickable(true)
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
        val cellInformation =
            mapPageViewModel.getCellInformationByCellId(currentLocationInformation.cellId)
                ?:return null
        if (mapChoice == null) {
            return null
        }
        if (mapChoice == "LAC") {
            if (!lacColors.containsKey(cellInformation.cellLac)) {
                lacColors.put(
                    cellInformation.cellLac,
                    systemColors[(lacColors.size + 1) % 27]
                )
            }
            return lacColors[cellInformation.cellLac]
        } else if (mapChoice == "CELL") {
            if (!cellColors.containsKey(cellInformation.cellId)) {
                cellColors.put(
                    cellInformation.cellId,
                    systemColors[(lacColors.size + 1) % 27]
                )
            }
            return cellColors[cellInformation.cellId]
        } else if (mapChoice == "PLMN") {
            if (!plmnColors.containsKey(cellInformation.cellPLMN)) {
                plmnColors.put(
                    cellInformation.cellPLMN,
                    systemColors[(lacColors.size + 1) % 27]
                )
            }
            return plmnColors[cellInformation.cellPLMN]
        } else if (mapChoice == "TEC") {
            if (!tecColors.containsKey(cellInformation.cellGeneration)) {
                tecColors.put(
                    cellInformation.cellGeneration,
                    systemColors[(lacColors.size + 1) % 27]
                )
            }
            return tecColors[cellInformation.cellGeneration]
        } else {
            return null
        }
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
package com.example.mobilenetworkproject.ui.mapPage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
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
    private val POLYLINE_STROKE_WIDTH_PX = 12

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
        // Add information to map
        this.addInformationToMap()

    }

    private fun addInformationToMap() {
        val locationsInformation = mapPageViewModel.selectAllLocationInformation()
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
                LatLng(
                    currentLocationInformation.LocationLatitude,
                    currentLocationInformation.LocationLongitude
                ),
                LatLng(
                    nextLocationInformation.LocationLatitude,
                    nextLocationInformation.LocationLongitude
                ),
                currentLocationInformation.cellId
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
        val cellInformation = mapPageViewModel.getCellInformationByCellId(cellId) ?: return
        myMap.addMarker(
            MarkerOptions()
                .position(LatLng(cellLatitude, cellLongitude))
                .title("title")
                .snippet("snippet")
        )
    }

    private fun addPolylineToMap(from: LatLng, to: LatLng, cellId: Long) {
        val polyline = myMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    from,
                    to
                )
        )
        this.stylePolyline(polyline, cellId)
    }

    private fun stylePolyline(polyline: Polyline, cellId: Long) {
        // TODO CHECK COLOR OF POLYLINE
        polyline.endCap = RoundCap()
        polyline.width = POLYLINE_STROKE_WIDTH_PX.toFloat()
        polyline.color = COLOR_BLACK_ARGB
        polyline.jointType = JointType.ROUND
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
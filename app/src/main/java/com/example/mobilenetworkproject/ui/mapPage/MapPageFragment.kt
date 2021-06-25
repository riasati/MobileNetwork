package com.example.mobilenetworkproject.ui.mapPage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mobilenetworkproject.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPolylineClickListener {

    private lateinit var mapPageViewModel: MapPageViewModel
    private lateinit var myMap: GoogleMap
    private val COLOR_BLACK_ARGB = -0x1000000
    private val POLYLINE_STROKE_WIDTH_PX = 12
    private val PATTERN_GAP_LENGTH_PX = 20
    private val DOT: PatternItem = Dot()
    private val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())
    private val PATTERN_POLYLINE_DOTTED = listOf(GAP, DOT)

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
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val opera = LatLng(-33.9320447, 151.1597271)
        this.addCellsInformationToMap()

        // Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.
        val polyline1 = googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    LatLng(-35.016, 143.321),
                    LatLng(-34.747, 145.592),
                    LatLng(-34.364, 147.891),
                    LatLng(-33.501, 150.217),
                    LatLng(-32.306, 149.248),
                    LatLng(-32.491, 147.309)
                )
        )

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
//        polyline1.tag = "B"
//        this.stylePolyline(polyline1)
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-23.684, 133.903), 4f))
//        googleMap.isMyLocationEnabled = true
        // Set listeners for click events.
//        googleMap.setOnPolylineClickListener(this)
//        googleMap.setOnPolygonClickListener(this)
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun stylePolyline(polyline: Polyline) {
        // Get the data object stored with the polyline.
        val type = polyline.tag?.toString() ?: ""
        when (type) {
            "A" -> {
                // Use a custom bitmap as the cap at the start of the line.
                polyline.startCap = CustomCap(
                    BitmapDescriptorFactory.fromResource(R.drawable.common_full_open_on_phone), 10f
                )
            }
            "B" -> {
                // Use a round cap at the start of the line.
                polyline.startCap = RoundCap()
            }
        }
        polyline.endCap = RoundCap()
        polyline.width = POLYLINE_STROKE_WIDTH_PX.toFloat()
        polyline.color = COLOR_BLACK_ARGB
        polyline.jointType = JointType.ROUND
    }

    override fun onPolylineClick(polyline: Polyline) {
        // Flip from solid stroke to dotted stroke pattern.
        if (polyline.pattern == null || !polyline.pattern!!.contains(DOT)) {
            polyline.pattern = PATTERN_POLYLINE_DOTTED
        } else {
            // The default pattern is a solid stroke.
            polyline.pattern = null
        }
        Toast.makeText(
            this, "Route type " + polyline.tag.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun addCellsInformationToMap() {
        val cellsInformation = mapPageViewModel.selectAllCellsInformation()
        for (cellInformation in cellsInformation) {
            // TODO GET CELL PLACE IF MOHAMMAD CANT'T
            myMap.addMarker(
                MarkerOptions()
                    .position(cellPlace)
                    .title(changePlaceInfo.get("title"))
                    .snippet(changePlaceInfo.get("integer"))
            )

        }
    }

    private fun getPlaceInfo(cell_information_id: Int): Map<String, String> {
        // TODO GET INFO FROM DB
        return mapOf(
            "title" to "title",
            "integer" to "15"
        )
    }

    private fun addToMap(result: List<List<LatLng>>) {
        val lineoption = PolylineOptions()
        for (i in result.indices) {
            lineoption.addAll(result[i])
            lineoption.width(10f)
            lineoption.color(Color.BLUE)
            lineoption.geodesic(true)
        }
        myMap.addPolyline(lineoption)

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
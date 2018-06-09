package co.fddittmar.appiz.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import co.fddittmar.appiz.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var placeName: String = "Place"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        placeName = getString(R.string.title_places)

        intent.extras.getDouble("latitude").let { latitude = it }
        intent.extras.getDouble("longitude").let { longitude = it }
        intent.extras.getString("placeName").let { placeName = it }

        supportActionBar?.title = placeName

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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
        mMap = googleMap

        println(latitude.toString() + longitude.toString())
        if(latitude != 0.0 && longitude != 0.0){
            val placeCoordinates = LatLng(latitude, longitude)
            mMap.addMarker(MarkerOptions().position(placeCoordinates))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeCoordinates, 15f))

        }

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

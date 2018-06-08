package co.fddittmar.appiz.view


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.fddittmar.appiz.MainActivity

import co.fddittmar.appiz.R
import co.fddittmar.appiz.db.DatabaseHandler
import co.fddittmar.appiz.model.Place
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_new_place.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NewPlaceFragment : Fragment() {

    private var mFusedLocationClient: FusedLocationProviderClient? = null

    /**
     * Represents a geographical location.
     */
    protected var mLastLocation: Location? = null

    var isEditMode: Boolean = false
    lateinit var place: Place
    lateinit var mainActivity: MainActivity
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    companion object {
        val TAG: String = NewPlaceFragment::class.java.simpleName
        fun newInstance() = NewPlaceFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_new_place, container, false)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mainActivity)

        if(mainActivity.locationPermission())
            getLastLocation()



        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val db = DatabaseHandler(this.context)

        if(arguments != null){
            isEditMode = true
            place = arguments.getSerializable("place") as Place

            etPlaceName.setText(place.name)
            etPlacePrice.setText(place.price.toString())
            etPlacePhoneNumber.setText(place.phoneNumber)

        }

        btnAddLocation.setOnClickListener({
            if(mLastLocation != null){
                mLastLocation?.latitude?.let { latitude= it }
                mLastLocation?.longitude?.let { longitude = it }

                Toast.makeText(mainActivity, getString(R.string.location_added), Toast.LENGTH_SHORT).show()
            }
        })



        btnSavePlace.setOnClickListener {
            val mAuth = FirebaseAuth.getInstance()

            if(isEditMode){
                if (mAuth.currentUser?.email != null &&!etPlaceName.text.isBlank() && !etPlacePrice.text.isBlank() && !etPlacePhoneNumber.text.isBlank()){
                    place.name = etPlaceName.text.toString()
                    place.price = etPlacePrice.text.toString().toFloat()
                    place.phoneNumber = etPlacePhoneNumber.text.toString()
                    if(latitude != 0.0 && longitude != 0.0){
                        place.latitude = latitude
                        place.longitude = longitude
                    }
                    db.updateData(place)
                    mainActivity.detachFragment()
                    mainActivity.supportFragmentManager.beginTransaction().add(R.id.container, PlacesFragment(), "places").commit()
                }

            }else {
                if (mAuth.currentUser?.email != null &&!etPlaceName.text.isBlank() && !etPlacePrice.text.isBlank() && !etPlacePhoneNumber.text.isBlank()){
                    val place = Place(mAuth.currentUser?.email!!, etPlaceName.text.toString(), etPlacePrice.text.toString().toFloat(), etPlacePhoneNumber.text.toString(), latitude, longitude)
                    db.insertData(place)
                }
                else{
                    Toast.makeText(this.context, getString(R.string.check_fields), Toast.LENGTH_LONG).show()
                }
            }





        }
    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient!!.lastLocation
                .addOnCompleteListener(mainActivity) { task ->
                    if (task.isSuccessful && task.result != null) {
                        mLastLocation = task.result

                    } else {
                        Log.w(TAG, "getLastLocation:exception", task.exception)
                    }
                }
    }

}

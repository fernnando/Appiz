package co.fddittmar.appiz.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import co.fddittmar.appiz.R
import co.fddittmar.appiz.model.Place
import kotlinx.android.synthetic.main.fragment_place_details.*
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.widget.Toast


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PlaceDetailsFragment : Fragment() {
    lateinit var place: Place

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        place = arguments.getSerializable("place") as Place

        tvPlaceTitle.text = place.name
        tvPrice.text = place.price.toString()
        tvPhoneNumber.text = place.phoneNumber

        btnShare.setOnClickListener({

            val placeDescription = "${place.name} - R$ ${place.price} \nTEL.:${place.phoneNumber}"
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
            whatsappIntent.`package` = "com.whatsapp"
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, placeDescription)
            try {
                activity.startActivity(whatsappIntent)
            } catch (ex: android.content.ActivityNotFoundException) {
                Toast.makeText(this.context, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show()
            }

        })

        btnShowMap.setOnClickListener({
            val intent = Intent(this.context, MapsActivity::class.java)
            intent.putExtra("latitude", place.latitude)
            intent.putExtra("longitude", place.longitude)
            startActivity(intent)
        })
    }


}

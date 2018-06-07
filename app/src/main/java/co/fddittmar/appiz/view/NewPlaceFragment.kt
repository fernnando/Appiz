package co.fddittmar.appiz.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import co.fddittmar.appiz.R
import co.fddittmar.appiz.db.DatabaseHandler
import co.fddittmar.appiz.model.Place
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

    companion object {
        val TAG: String = NewPlaceFragment::class.java.simpleName
        fun newInstance() = NewPlaceFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_new_place, container, false)






        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DatabaseHandler(this.context)


        btnSavePlace.setOnClickListener {

            if (!etPlaceName.text.isBlank() && !etPlacePrice.text.isBlank() && !etPlacePhoneNumber.text.isBlank()){
                val place = Place(etPlaceName.text.toString(), etPlacePrice.text.toString().toFloat(), etPlacePhoneNumber.text.toString())
                db.insertData(place)
            }
            else{
                Toast.makeText(this.context, "There was an error during your request.", Toast.LENGTH_LONG).show()
            }


        }
    }
}

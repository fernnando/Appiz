package co.fddittmar.appiz.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import co.fddittmar.appiz.R
import co.fddittmar.appiz.adapter.PlaceAdapter
import co.fddittmar.appiz.db.DatabaseHandler
import co.fddittmar.appiz.model.Place
import kotlinx.android.synthetic.main.fragment_places.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PlacesFragment : Fragment() {


    private var places: MutableList<Place> = mutableListOf()

    companion object {
        val TAG: String = PlacesFragment::class.java.simpleName
        fun newInstance() = PlacesFragment()


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DatabaseHandler(this.context)
        //db.deleteData()

        places = db.readData()
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity)
        rvPlaces.layoutManager = layoutManager
        rvPlaces.adapter = PlaceAdapter(places, activity)
    }


}

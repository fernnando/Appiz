package co.fddittmar.appiz.view


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.fddittmar.appiz.MainActivity

import co.fddittmar.appiz.R
import co.fddittmar.appiz.adapter.PlaceAdapter
import co.fddittmar.appiz.db.DatabaseHandler
import co.fddittmar.appiz.model.Place
import com.google.firebase.auth.FirebaseAuth
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


    var places: MutableList<Place> = mutableListOf()

    private lateinit var mainActivity: MainActivity
    val mAuth = FirebaseAuth.getInstance()
    lateinit var db: DatabaseHandler

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
        db = DatabaseHandler(this.context)

        //db.deleteData()

        reloadData()

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity)
        rvPlaces.layoutManager = layoutManager
        rvPlaces.adapter = PlaceAdapter(places, mainActivity, this)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    fun reloadData(){
        places = db.readData(mAuth.currentUser?.email!!)
    }


}

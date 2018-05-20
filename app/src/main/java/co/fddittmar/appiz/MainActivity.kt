package co.fddittmar.appiz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import co.fddittmar.appiz.adapter.PlaceAdapter
import co.fddittmar.appiz.model.Place
import co.fddittmar.appiz.network.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var places: MutableList<Place> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        places.add(Place("Teste", "Testando"))
        getPlaces()

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvPlaces.layoutManager = layoutManager
        rvPlaces.adapter = PlaceAdapter(places, this)

    }

    private fun getPlaces(){

        val placesList: MutableList<Place> = mutableListOf()
        println("TESTANDO")
        val observable = APIService.create().getPlace()
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { books ->
                    places.add(books)
                    println(books)
                    rvPlaces.adapter.notifyDataSetChanged()
                },
                        { error ->
                            Toast.makeText(this, error.message,Toast.LENGTH_SHORT).show()
                        } )
    }
}


/*

{
                    place: Place? ->
                    if (place != null) {
                        placesList.add(place)
                        rvPlaces.adapter.notifyDataSetChanged()
                    }
                    println("TESTE")

                }
 */

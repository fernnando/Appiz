package co.fddittmar.appiz.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.fddittmar.appiz.R
import co.fddittmar.appiz.model.Place
import kotlinx.android.synthetic.main.item_place.view.*

/**
 * Created by Fernnando on 14/04/2018.
 */
class PlaceAdapter(private val places: List<Place>,
                   private val context: Context) : Adapter<PlaceAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val place = places[position]

        holder?.bindView(place)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindView(place: Place) {
            val title = itemView.tvTitle
            val description = itemView.tvDescription

            title.text = place.name
            description.text = place.price.toString()
        }
    }
}


package co.fddittmar.appiz.adapter

import android.Manifest
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.fddittmar.appiz.R
import co.fddittmar.appiz.model.Place
import kotlinx.android.synthetic.main.item_place.view.*
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import co.fddittmar.appiz.MainActivity


/**
 * Created by Fernnando on 14/04/2018.
 */
class PlaceAdapter(private val places: List<Place>,
                   private val context: MainActivity) : Adapter<PlaceAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val place = places[position]

        holder?.bindView(place, context)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindView(place: Place, context: MainActivity) {
            val title = itemView.tvTitle
            val description = itemView.tvDescription

            title.text = place.name
            description.text = place.price.toString()

            itemView.btnCall.setOnClickListener({

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            context.phoneNumberPermission()


                        }
                        else{
                            val intent = Intent(Intent.ACTION_CALL)

                            intent.data = Uri.parse("tel:" + place.phoneNumber)
                            println(place.phoneNumber)
                            startActivity(context, intent, null)
                        }
                    }


                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            })

        }


    }
}


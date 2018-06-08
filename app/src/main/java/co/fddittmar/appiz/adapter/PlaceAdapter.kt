package co.fddittmar.appiz.adapter

import android.Manifest
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
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.widget.Toast
import co.fddittmar.appiz.MainActivity
import co.fddittmar.appiz.db.DatabaseHandler
import co.fddittmar.appiz.view.NewPlaceFragment
import co.fddittmar.appiz.view.PlaceDetailsFragment
import co.fddittmar.appiz.view.PlacesFragment
import kotlinx.android.synthetic.main.fragment_places.*
import java.io.Serializable


/**
 * Created by Fernnando on 14/04/2018.
 */
class PlaceAdapter(private val places: List<Place>,
                   private val context: MainActivity,
                   private val placesFragment: PlacesFragment) : Adapter<PlaceAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val place = places[position]

        holder?.bindView(place, context, placesFragment)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindView(place: Place, context: MainActivity, placesFragment: PlacesFragment) {
            val title = itemView.tvTitle
            val description = itemView.tvDescription

            title.text = place.name
            description.text = place.price.toString()

            itemView.setOnClickListener({
                context.detachFragment()
                val placeFragment = PlaceDetailsFragment()
                val bundle = Bundle()
                bundle.putSerializable("place", place as Serializable)
                placeFragment.arguments = bundle


                context.supportFragmentManager.beginTransaction().add(R.id.container, placeFragment, "details").commit()
            })

            itemView.btnEdit.setOnClickListener {
                context.detachFragment()
                val newPlaceFragment = NewPlaceFragment()
                val bundle = Bundle()
                bundle.putSerializable("place", place as Serializable)
                newPlaceFragment.arguments = bundle


                context.supportFragmentManager.beginTransaction().add(R.id.container, newPlaceFragment, "edit").commit()
            }

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

            itemView.setOnLongClickListener {
                val dialog = AlertDialog.Builder(context).setTitle(R.string.delete).setMessage(R.string.delete_confirmation)
                        .setPositiveButton(R.string.confirm, { dialog, i ->
                            val db = DatabaseHandler(context)
                            db.deleteData(place.id.toString())
                            placesFragment.places.removeAt(adapterPosition)
                            placesFragment.rvPlaces.adapter.notifyItemRemoved(adapterPosition)

                            Toast.makeText(context, R.string.delete_message, Toast.LENGTH_LONG).show()
                        })
                        .setNegativeButton(R.string.cancel, { dialog, i -> })

                dialog.show()


                // Display the alert dialog on app interface

                return@setOnLongClickListener true


            }

        }


    }
}


package co.fddittmar.appiz.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Fernnando on 14/04/2018.
 */
class Place {
    constructor(name: String, price: Float, phoneNumber: String) {
        this.name = name
        this.price = price
        this.phoneNumber = phoneNumber
    }

    constructor(name: String, price: Float, latitude: Double, longitude: Double){

    }


    var id: Int = 0
    var name: String = ""
    var price: Float = 0.0f
    var phoneNumber: String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0
}




package co.fddittmar.appiz.network

import co.fddittmar.appiz.model.Place
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by Fernnando on 14/04/2018.
 */

interface APIService{



    @GET("people/1")
    fun getPlace() : Call<Place>


    companion object Factory {
        val BASE_URL: String = "https://swapi.co/api/"

        fun create(): APIService {
            val retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(APIService::class.java)
        }
    }
}


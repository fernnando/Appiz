package co.fddittmar.appiz.network

import co.fddittmar.appiz.model.Place
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Fernnando on 14/04/2018.
 */

interface APIService{
    @GET("people/1")
    fun getPlace() : Observable<Place>


    companion object Factory {
        fun create(): APIService {
            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.create())
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .baseUrl("https://swapi.co/api/")
                    .build()

            return retrofit.create(APIService::class.java)
        }
    }
}


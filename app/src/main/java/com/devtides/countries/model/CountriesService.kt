package com.devtides.countries.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountriesService {

    private val BASE_URL = "https://raw.githubusercontent.com"
    private val api: CountriesApi

    init{
        /* Basically creates the framework for retrofit which will help us to get the information from the backend
        Single is basically unobservable that only omits one value and then closes
        * */
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
             // Transform Json code into this Kotlin code
            .addConverterFactory(GsonConverterFactory.create())
             // Transform our data that we have here in the type country into an observable variable
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }

    fun getCountries(): Single<List<Country>> {
        return api.getCountries()
    }
}
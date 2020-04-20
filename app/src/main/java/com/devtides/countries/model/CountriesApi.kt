package com.devtides.countries.model

import io.reactivex.Single
import retrofit2.http.GET

// Call the Countries API
interface CountriesApi {

    @GET("DevTides/countries/master/countriesV2.json")
    // It's an observable that emits one variable and then closes
    fun getCountries(): Single<List<Country>>
//
//    @GET("endpoint2")
//    fun getEndpoint2Data()

}
package com.devtides.countries.di

import com.devtides.countries.model.CountriesApi
import com.devtides.countries.model.CountriesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
class ApiModule {
    private val BASE_URL = "https://raw.githubusercontent.com"

    /* Basically creates the framework for retrofit which will help us to get the information from the backend
Single is basically unobservable that only omits one value and then closes
* */
    @Provides
    fun provideCountriesApi(): CountriesApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            // Transform Json code into this Kotlin code
            .addConverterFactory(GsonConverterFactory.create())
            // Transform our data that we have here in the type country into an observable variable
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }
    @Provides
    fun provideCountriesService():CountriesService{
        return CountriesService()
    }
}

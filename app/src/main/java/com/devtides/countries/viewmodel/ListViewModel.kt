package com.devtides.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devtides.countries.di.DaggerApiComponent
import com.devtides.countries.model.CountriesService
import com.devtides.countries.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel: ViewModel() {

    @Inject lateinit var countriesService: CountriesService

    init {
        DaggerApiComponent.create().inject(this)
    }
//
//    private val countriesService = CountriesService()
    private val disposable = CompositeDisposable()

    // It's just the subscriber to this variable will get notified automatically.
    val countries = MutableLiveData<List<Country>>()
    // If there is an error in loading the data it's just going to have true for error and false for no error.
    val countryLoadError = MutableLiveData<Boolean>()
    // Whether it in the ViewModel or the listViewmModel is in the process of loading the data from the backend.
    val loading = MutableLiveData<Boolean>()

    /* In fact, actual functionality of the view model is the fetchCountries(), and we do not want to
    expose the function that actually does the functionality. Because We do want this refresh will be a
    public method that will be call from outside. It means, outside callers do not need to know how this operation
    is performed.
     */
    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        // Notify that everyone listening that loading is starting
        loading.value = true
        disposable.add(
            // We called the CountriesService.kt and getCountries() and this simply returns a single list country
            countriesService.getCountries()
                // All the processing that get countries does is gonna be on a separate thread
                .subscribeOn(Schedulers.newThread())
                // The result of that processing needs to be on the main thread that the user is seeing so that we can display that information correctly
                .observeOn(AndroidSchedulers.mainThread())
                // Need to define the functionality what we're gonna do when we get the information
                .subscribeWith(object:DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(value: List<Country>?) {
                        countries.value = value
                        countryLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        countryLoadError.value = true
                        loading.value = false
                    }

                })


//        val mockData = listOf(
//            Country("CountryA"),
//            Country("CountryB"),
//            Country("CountryC"),
//            Country("CountryD"),
//            Country("CountryE"),
//            Country("CountryF"),
//            Country("CountryG"),
//            Country("CountryH"),
//            Country("CountryI"),
//            Country("CountryJ")
//        )
//
//        // I have had no error in loading the data so I need to notify all the subscribers.
//        countryLoadError.value = false
//        // Because it's not loading
//        loading.value = false
//        /* whoever is subscribed to my country's variable, Once I get the information from the backend
//        I have a list of countries and I update my local variable to say these are my countries that I got
//        from the backend and whoever is listening will receive that information because I assign that here */
//        countries.value = mockData
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
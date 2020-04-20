package com.devtides.countries.di

import com.devtides.countries.model.CountriesService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    // This function will help dagger inject the right components from API module into countries service.
    fun inject(service: CountriesService)
}
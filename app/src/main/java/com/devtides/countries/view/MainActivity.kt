package com.devtides.countries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devtides.countries.R
import com.devtides.countries.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //lateinit: I will take care to instantiate this variable before I use it
    lateinit var viewModel: ListViewModel
    private val countriesAdapter = CountryListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // this is the way we instantiate the view model
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        observeViewModel()
    }

    fun observeViewModel()  {
        viewModel.countries.observe(this, Observer { countries ->
            countries?.let {
                countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it)}
        })

        viewModel.countryLoadError.observe(this, Observer {isError ->
            isError?.let {list_error.visibility = if(it) View.VISIBLE else View.GONE}
        })

        viewModel.loading.observe(this, Observer{ isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it)  {
                    list_error.visibility = View.GONE
                    countriesList.visibility = View.GONE
                }
            }
        })


    }
}

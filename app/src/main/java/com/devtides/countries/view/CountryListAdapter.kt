package com.devtides.countries.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devtides.countries.R
import com.devtides.countries.model.Country
import com.devtides.countries.util.getProgressDrawable
import com.devtides.countries.util.loadImage
import kotlinx.android.synthetic.main.item_country.view.*

class CountryListAdapter(var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        /* This is very important because what it tells the Android system is that we have a new list of countries
        you have to redo the whole list. You have to re initialize the whole thing.*/
        notifyDataSetChanged()
    }

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.imageView
        private val countryName = view.name
        private val countryCapital = view.capital
        private val progressDrawable = getProgressDrawable(view.context)


        fun bind(country: Country) {
            countryName.text = country.countryName
            countryCapital.text = country.capital
            // LoadImage passed the flag url and progressDrawable
            // ProgressDrawable is basically a spinner that will show up inside the image that will allow the user to notice that the image is being loaded
            imageView.loadImage(country.flag, progressDrawable)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
    )
//    Return the number of elements that we have
    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }
}
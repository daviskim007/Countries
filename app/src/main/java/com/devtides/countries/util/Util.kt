package com.devtides.countries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.devtides.countries.R

fun getProgressDrawable(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        // Create a little spinner that will display in the image before it is loaded
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}


fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable)   {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}
package com.rivas.testandroiddeveloper.utils.extensions

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rivas.testandroiddeveloper.AndroidApp
import com.rivas.testandroiddeveloper.R

internal fun ImageView.loadImage(image: String) {

    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 10f
    circularProgressDrawable.centerRadius = 50f
    circularProgressDrawable.start()
    Glide.with(AndroidApp.appContext).applyDefaultRequestOptions(
        RequestOptions().placeholder(
            circularProgressDrawable
        ).error(
            R.drawable.no_image_place_holder
        )).load(context.getString(R.string.image_path_url)+image).into(this)
}

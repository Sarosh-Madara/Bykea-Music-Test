package com.project.music.app.utils

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.viewbinding.BuildConfig
import com.bumptech.glide.Glide
import com.project.music.app.BuildConfig.APPLICATION_ID
import com.project.music.app.R
import java.io.File
import java.util.*



fun Context.loadImageFromURL(imageURL: String, imageView: ImageView) {
    Glide
        .with(this)
        .load(imageURL)
        .centerCrop()
        .placeholder(R.drawable.ic_launcher_background)
        .into(imageView);
}
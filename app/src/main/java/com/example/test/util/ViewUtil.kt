package com.example.test.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.LiveData

// functions to show show
fun Context.myToast(message: String)
{
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// Functions to show progressbar
fun ProgressBar.show(){

    visibility = View.VISIBLE
}

// Functions to hide Progressbar
fun ProgressBar.hide(){

    visibility = View.GONE
}
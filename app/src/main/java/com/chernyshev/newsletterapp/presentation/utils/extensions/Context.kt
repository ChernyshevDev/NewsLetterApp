package com.chernyshev.newsletterapp.presentation.utils.extensions

import android.content.Context
import android.widget.Toast
import com.chernyshev.newsletterapp.R

fun Context.showNotImplementedToast() =
    Toast.makeText(this, this.getString(R.string.not_implemented), Toast.LENGTH_SHORT).show()
package com.bferrari.mvvmsample.extensions

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri

val ConnectivityManager.isConnected: Boolean
    get() = activeNetworkInfo?.isConnected ?: false

fun Context.openInBrowser(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}
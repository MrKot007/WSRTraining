package com.example.session_2training

import android.app.AlertDialog
import android.content.Context

fun Context.showAlertDialog(reason: String) {
    AlertDialog.Builder(this)
        .setTitle("An error has occured")
        .setMessage(reason)
        .setPositiveButton("OK", null)
        .create().show()
}
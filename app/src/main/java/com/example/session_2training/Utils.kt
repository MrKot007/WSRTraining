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

fun String.checkEmail() : Boolean{
    val name = this.substringBefore("@")
    val domen = this.substringAfter("@")
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches() && name.lowercase() == name && domen.lowercase() == domen
}
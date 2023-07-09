package com.example.session_2training

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import com.google.common.hash.Hashing

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

fun SharedPreferences.savePasswordSHA512(password: String) {
    val safePassword = Hashing.sha512().hashString(password, Charsets.UTF_8).toString()
    edit()
        .putString("password", safePassword)
        .apply()
}
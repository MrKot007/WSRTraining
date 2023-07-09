package com.example.a0sessiontry2

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import com.google.common.hash.Hashing

fun Context.showAlertDialog(reason: String) {
    AlertDialog.Builder(this)
        .setTitle("An error has occured!")
        .setMessage(reason)
        .setPositiveButton("OK", null)
        .create().show()
}
fun String.checkEmail() : Boolean {
    val name = this.substringBefore("@")
    val domename = this.substringAfter("@")
    return name.lowercase() == name && domename.lowercase() == domename && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
fun SharedPreferences.savePasswordSHA512(password: String) {
    val safePassword = Hashing.sha512().hashString(password, Charsets.UTF_8).toString()
    edit()
        .putString("password", safePassword)
        .apply()
}
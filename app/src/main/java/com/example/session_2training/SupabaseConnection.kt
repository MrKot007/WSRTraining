package com.example.session_2training

import android.content.Context
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.put

object SupabaseConnection {
    val client = createSupabaseClient(
        "https://wopbuxosqgpzvnqmualj.supabase.co",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndvcGJ1eG9zcWdwenZucW11YWxqIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODg4MzUyODQsImV4cCI6MjAwNDQxMTI4NH0.p1UXq0Eum3oExz1pLKouMkNgPBngroVSm98J-Iptj4g"
    ) {
        install(GoTrue)
        install(Postgrest)
        install(Storage)
    }

    suspend fun signUp(context: Context, email: String, password: String, fullname: String, phone: String) : Boolean {
        try {
            client.gotrue.signUpWith(Email) {
                this.email = email
                this.password = password
                this.data = buildJsonObject {
                    put("fullname", fullname)
                    put("phone", phone)
                    put("email", email)
                }
            }
            return true
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
        return false
    }

    suspend fun insertNewUser(context: Context, user: DataUserInsertRow) : DataUserRow?{
        try {
            val result = client.postgrest["Profiles"].insert(user).body?.jsonArray ?: return null
            return Json.decodeFromJsonElement(result)
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
        return null
    }

    suspend fun checkIfUserExists(context: Context, idUser: String) : Boolean{
        try {
            return client.postgrest["Profiles"].select { eq("user_id", idUser) }.body?.jsonArray?.size != 0
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
        return false
    }

    suspend fun logInViaEmail(context: Context, email: String, password: String) {
        try {
            client.gotrue.loginWith(Email) {
                this.email = email
                this.password = password
            }
            val user = getUser(context) ?: return
            if (!checkIfUserExists(context, user.id)) {
                val fullname = user.userMetadata?.get("fullname")?.toString()?.replace("\"", "") ?: ""
                val phone = user.userMetadata?.get("phone")?.toString()?.replace("\"","") ?: ""
                insertNewUser(context, DataUserInsertRow(fullname, phone, user.id))
            }
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
    }

    fun getUser(context: Context): UserInfo? {
        val user = client.gotrue.currentUserOrNull()
        if (user == null) {
            context.showAlertDialog("User not found")
        }
        return user
    }

    suspend fun geUserData(context: Context, idUser: String) : DataUserRow? {
        try {
            val user = getUser(context)
            val result = client.postgrest["Profiles"].select { eq("user_id", idUser) }.body?.jsonArray?.get(0) ?: return null
            return Json.decodeFromJsonElement(result)
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
        return null
    }

    suspend fun collectCallback(context: Context, callback: SupabaseConnectionCallback) {
        try {
            client.gotrue.sessionStatus.collect {
                when(it) {
                    is SessionStatus.Authenticated -> {
                        callback.onAuth(it.session.user)
                    }
                    SessionStatus.LoadingFromStorage -> {
                        callback.onLoading()
                    }
                    SessionStatus.NetworkError -> {
                        callback.onNetworkError()
                    }
                    SessionStatus.NotAuthenticated -> {
                        callback.onUnauthorizedEntry()
                    }

                }
            }
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
    }
}

interface SupabaseConnectionCallback {

    fun onAuth(user: UserInfo?)

    fun onLoading()

    fun onNetworkError()

    fun onUnauthorizedEntry()
}
package com.example.a0sessiontry2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.put

object SupabaseConnection {
    val client = createSupabaseClient(
        "https://wopbuxosqgpzvnqmualj.supabase.co",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndvcGJ1eG9zcWdwenZucW11YWxqIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODg4MzUyODQsImV4cCI6MjAwNDQxMTI4NH0.p1UXq0Eum3oExz1pLKouMkNgPBngroVSm98J-Iptj4g"
    )
    {
        install(GoTrue)
        install(Postgrest)
        install(Storage)
    }

    suspend fun logInWithEmail(context: Context, email: String, password: String) {
        try {
            client.gotrue.loginWith(Email) {
                this.email = email
                this.password = password
            }
            val user = getUser(context) ?: return
            if (!checkIfUserExists(context, user.id)) {
                val fullname = user.userMetadata?.get("fullname")?.toString()?.replace("\"", "") ?: ""
                val phone = user.userMetadata?.get("phone")?.toString()?.replace("\"", "") ?: ""
                insertNewUser(context, DataUserInsertRow(fullname, phone, user.id))
            }
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
    }

    suspend fun signUp(context: Context, fullname: String, phone: String, email: String, password: String) : Boolean {
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

    suspend fun insertNewUser(context: Context, user: DataUserInsertRow) : DataUserRow? {
        try {
            val result = client.postgrest["Profiles"].insert(user).body?.jsonArray?.get(0) ?: return null
            return Json.decodeFromJsonElement(result)
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
        return null
    }

    suspend fun checkIfUserExists(context: Context, idUser: String) : Boolean {
        try {
            return client.postgrest["Profiles"].select { eq("user_id", idUser) }.body?.jsonArray?.size != 0
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
        return false
    }

    fun getUser(context: Context) : UserInfo? {
        val user = client.gotrue.currentUserOrNull()
        if (user == null) {
            context.showAlertDialog("No user found")
        }
        return user
    }

    suspend fun getUserData(context: Context, idUser: String) : DataUserRow? {
        try {
            val result = client.postgrest["Profiles"].select { eq("user_id", idUser) }.body?.jsonArray?.get(0) ?: return null
            return Json.decodeFromJsonElement(result)
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
        return null
    }

    suspend fun collectCallback(context: Context, callback: SupabaseConnextionCallback) {
        try {
            client.gotrue.sessionStatus.collect {
                if (it is SessionStatus.Authenticated) {
                    callback.onAuth(it.session.user)
                }
            }
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
    }

    suspend fun uploadAvatar(context: Context, bytes: ByteArray) {
        try {
            val user = getUser(context) ?: return
            val name = "avatar-${user.id}.jpeg"
            try {
                client.storage["Avatars"].upload(name, bytes, false)
            }catch (e: Exception) {
                client.storage["Avatars"].update(name, bytes, false)
            }
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
    }

    suspend fun getAvatar(context: Context): Bitmap? {
        try {
            val user = getUser(context) ?: return null
            val bytes = client.storage["Avatars"].downloadPublic("avatar-${user.id}.jpeg")
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }catch (e: Exception) {
            context.showAlertDialog(e.message ?: "")
        }
        return null
    }
}
interface SupabaseConnextionCallback {
    fun onAuth(user: UserInfo?)
}
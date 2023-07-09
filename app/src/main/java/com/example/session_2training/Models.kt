package com.example.session_2training

import kotlinx.serialization.Serializable

@Serializable
data class DataUserRow(
    val id: Int? = null,
    val full_name: String,
    val phone: String,
    val user_id: String
)
@Serializable
data class DataUserInsertRow(
    val full_name: String,
    val phone: String,
    val user_id: String
)
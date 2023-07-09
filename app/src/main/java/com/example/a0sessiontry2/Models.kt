package com.example.a0sessiontry2

import kotlinx.serialization.Serializable

@Serializable
data class DataUserInsertRow(
    val full_name: String,
    val phone: String,
    val user_id: String
)
@Serializable
data class DataUserRow(
    val id: Int? = null,
    val full_name: String,
    val phone: String,
    val user_id: String
)
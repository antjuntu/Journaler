package com.journaler.api

import com.google.gson.annotations.SerializedName

/**
 * The API call will return a JSON that will be deserialized into
 * the JournalerApiToken data class
 */
data class JournalerApiToken(
    @SerializedName("id_token") val token: String,
    val expires: Long
)
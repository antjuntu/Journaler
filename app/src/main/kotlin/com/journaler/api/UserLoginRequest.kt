package com.journaler.api

/**
 * Contains authentication credentials
 */
data class UserLoginRequest(
    val username: String,
    val password: String
)
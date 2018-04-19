package com.journaler.api

/**
 * We will hold the latest token instance inside the TokenManager object.
 */
object TokenManager {
    var currentToken = JournalerApiToken("", -1)
}
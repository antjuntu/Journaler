package com.journaler.api

import com.journaler.model.Note
import com.journaler.model.Todo
import retrofit2.Call
import retrofit2.http.*

/**
 * Retrofit turns HTTP API into a Kotlin interface
 */
interface JournalerBackendService {

    @POST("user/authenticate")
    fun login(
        @HeaderMap headers: Map<String, String>,
        @Body payload: UserLoginRequest
    ): Call<JournalerApiToken>

    /**
     * We will need a token for all other calls, and we will
     * put its content into the header of each call
     */

    @GET("entity/note")
    fun getNotes(
        @HeaderMap headers: Map<String, String>
    ): Call<List<Note>>

    @GET("entity/todo")
    fun getTodos(
        @HeaderMap headers: Map<String, String>
    ): Call<List<Todo>>

    @PUT("entity/note")
    fun publishNotes(
        @HeaderMap headers: Map<String, String>,
        @Body payload: List<Note>
    ): Call<Unit>

    @PUT("entity/todo")
    fun publishTodos(
        @HeaderMap headers: Map<String, String>,
        @Body payload: List<Todo>
    ): Call<Unit>

    @DELETE("entity/note")
    fun removeNotes(
        @HeaderMap headers: Map<String, String>,
        @Body payload: List<Note>
    ): Call<Unit>

    @DELETE("entity/todo")
    fun removeTodos(
        @HeaderMap headers: Map<String, String>,
        @Body payload: List<Todo>
    ): Call<Unit>

}

































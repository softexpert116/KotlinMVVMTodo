package com.example.test.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @FormUrlEncoded
    @POST("auth/login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("signup")
    fun userSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String
    ) : Call<ResponseBody>

    @GET("todos")
    fun todoListGet(
        @Header("Authorization") Authorization: String
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("todos")
    fun mainTodoCreate(
        @Header("Authorization") Authorization: String,
        @Field("title") title: String
    ) : Call<ResponseBody>

    @DELETE("todos/{id}")
    fun todoDelete(
        @Header("Authorization") Authorization: String,
        @Path("id") id: Int
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("todos/{id}/items")
    fun subTodoCreate(
        @Header("Authorization") Authorization: String,
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("done") done: Boolean
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @PUT("todos/{id}/items/{item_id}")
    fun subTodoUpdate(
        @Header("Authorization") Authorization: String,
        @Path("id") id: Int,
        @Path("item_id") item_id: Int,
        @Field("name") name: String,
        @Field("done") done: Boolean
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @PATCH("todos/{id}/items/{item_id}")
    fun subTodoPatch(
        @Header("Authorization") Authorization: String,
        @Path("id") id: Int,
        @Path("item_id") item_id: Int,
        @Field("done") done: Boolean
    ) : Call<ResponseBody>

    companion object{
        operator fun invoke(): MyApi
        {
            return Retrofit.Builder()
                .baseUrl("https://todos.flexhire.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(MyApi::class.java)
        }
    }

}
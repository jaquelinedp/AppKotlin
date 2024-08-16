package com.jackie.delivery.routes

import com.jackie.delivery.models.ResponseHttp
import com.jackie.delivery.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface  UsersRoutes{
@POST("users/create")
fun register(@Body user: User): Call<ResponseHttp>
@FormUrlEncoded
@POST("users/login")
fun login(@Field("email") email:String, @Field("password") password:String): Call<ResponseHttp>
}
package com.jackie.delivery.providers

import com.jackie.delivery.api.ApiRoutes
import com.jackie.delivery.models.ResponseHttp
import com.jackie.delivery.models.User
import com.jackie.delivery.routes.UsersRoutes
import retrofit2.Call

class UserProvider {

private var usersRoutes:UsersRoutes? = null
    init {
        val api = ApiRoutes()
        usersRoutes = api.getUsersRoutes()
    }

    fun register (user: User):Call<ResponseHttp>?{
        return  usersRoutes?.register(user)
    }
    fun login(email:String, password:String):Call<ResponseHttp>?{
        return usersRoutes?.login(email, password)
    }
}
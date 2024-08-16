package com.jackie.delivery.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.jackie.delivery.activities.MainActivity

class SharedPref(activity: Activity){
    private var prefs: SharedPreferences? = null
    init {
        prefs = activity.getSharedPreferences("com.jackie.delivery", Context.MODE_PRIVATE)
    }
    fun save(key: String, objeto: Any){
        try {
            val gson = Gson()
            val json = gson.toJson(objeto)
            with(prefs?.edit()){
                this?.putString(key, json)
                this?.commit()

            }
        } catch (e: Exception){
            Log.d("SharedPref", "Error al guardar el objeto")
        }
    }
    fun getData(key: String): String?{
        val data = prefs?.getString(key, "")
        return data
}
fun remove( key: String){
    prefs?.edit()?.remove()?.apply()
}
}
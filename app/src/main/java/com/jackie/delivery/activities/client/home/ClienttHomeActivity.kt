package com.jackie.delivery.activities.client.home

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.jackie.delivery.R
import com.jackie.delivery.models.User
import com.jackie.delivery.utils.SharedPref

class ClienttHomeActivity : AppCompatActivity() {
    private val TAG = "ClientHomeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_clientt_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun getUserFromSession(){
        val sharedPref = SharedPref(this)
        val gson = Gson()
        val user = sharedPref.getData("user")
        if (user != null){
            val userJson = user as String
            val gson = Gson()
            val userObject = gson.fromJson(userJson, User::class.java)
            Log.d("ClientHomeActivity", "User: $userObject")
        }
    }
}
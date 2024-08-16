package com.jackie.delivery.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.jackie.delivery.R
import com.jackie.delivery.activities.client.home.ClienttHomeActivity
import com.jackie.delivery.models.ResponseHttp
import com.jackie.delivery.models.User
import com.jackie.delivery.providers.UserProvider
import com.jackie.delivery.utils.SharedPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {


    var imageViewGoToRegister: ImageView? = null
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var buttonLogin: Button? = null
    var usersProvider = UserProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageViewGoToRegister = findViewById(R.id.imageview_go_to_register)
        editTextEmail = findViewById(R.id.edittext_email)
        editTextPassword = findViewById(R.id.edittext_password)
        buttonLogin = findViewById(R.id.btn_login)

        imageViewGoToRegister?.setOnClickListener { goToRegister() }
        buttonLogin?.setOnClickListener { login() }
    }


    private fun login() {
        val email = editTextEmail?.text.toString() // NULL POINTER EXCEPTION
        val password = editTextPassword?.text.toString()

        if (isValidForm(email, password)) {
            Toast.makeText(this, "El formulario es valido", Toast.LENGTH_LONG).show()
            usersProvider.login(email, password)?.enqueue(object: Callback<ResponseHttp>{
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
             Log.d("MainActivity", "Response: ${response.body()}")
                    if (response.isSuccessful ){

                        Toast.makeText(this@MainActivity, response?.body()?.message, Toast.LENGTH_LONG).show()
                  saveUserInSession(response.body()?.data.toString())
                        goToHome()
                    }
                    else{
                        Toast.makeText(this@MainActivity, "Los datos no son correctos", Toast.LENGTH_LONG).show()

                    }
                }


                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    Log.d("MainActivity", "Error en la peticion ${t.message}")
                    Toast.makeText(this@MainActivity, "Error en la peticion", Toast.LENGTH_LONG).show()
                }
            })
        }
        else {
            Toast.makeText(this, "Ingrese un email valido", Toast.LENGTH_LONG).show()

        }

        Log.d("MainActivity", "El email es: $email")
        Log.d("MainActivity", "El password es: $password")
    }
private fun goToHome(){
    val i = Intent(this, ClienttHomeActivity::class.java)
    startActivity(i)
}
    private fun saveUserInSession(data: String) {
        val SharedPref = SharedPref(this)
        val gson = Gson()
        val user = gson.fromJson(data, User::class.java)
     SharedPref.save("user", user)

    }
    private fun getUserFromSession(){
        val sharedPref = SharedPref(this)
        val gson = Gson()
        val user = sharedPref.getData("user")
        if (user != null){
            val userJson = user as String
            val gson = Gson()
            val userObject = gson.fromJson(userJson, User::class.java)
            goToHome()
        }
    }
    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun isValidForm(email: String, password: String): Boolean {

        if (email.isBlank()) {
            return false
        }

        if (password.isBlank()) {
            return false
        }

        if (!email.isEmailValid()) {
            return false
        }

        return true
    }

    private fun goToRegister() {
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }

}
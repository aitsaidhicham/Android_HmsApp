package com.dev.hms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Authentication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        setSupportActionBar(findViewById(R.id.my_toolbar))
    }

    fun createAccount(view:View){
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }
}



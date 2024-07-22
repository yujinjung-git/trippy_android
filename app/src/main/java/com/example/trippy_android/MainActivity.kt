package com.example.trippy_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trippy_android.User.UserRetrofitService
import com.example.trippy_android.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.here.setOnClickListener{
            loginExtension()
        }
    }

    private fun loginExtension() {
        val usService = UserRetrofitService(this)
        usService.loginExtension()
    }
}
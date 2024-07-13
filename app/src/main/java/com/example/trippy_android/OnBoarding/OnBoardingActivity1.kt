package com.example.trippy_android.OnBoarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trippy_android.databinding.ActivityOnBoarding1Binding

class OnBoardingActivity1 : AppCompatActivity() {
    lateinit var binding1:ActivityOnBoarding1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding1.root)
    }
}
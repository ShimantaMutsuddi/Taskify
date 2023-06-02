package com.mutsuddi_s.taskify.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mutsuddi_s.taskify.R
import com.mutsuddi_s.taskify.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
   // private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
    }
}
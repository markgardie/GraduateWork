package com.markgardie.graduatework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.navArgs
import com.markgardie.graduatework.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PriceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price)

    }
}
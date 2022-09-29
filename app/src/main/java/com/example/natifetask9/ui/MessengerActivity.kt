package com.example.natifetask9.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.natifetask9.R
import com.example.natifetask9.databinding.ActivityMessengerBinding

class MessengerActivity : AppCompatActivity() {

    private var binding: ActivityMessengerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessengerBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
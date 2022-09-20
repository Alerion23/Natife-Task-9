package com.example.natifetask9.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.natifetask9.databinding.ActivityAuthBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : AppCompatActivity() {

    private var binding: ActivityAuthBinding? = null
    private val viewModel by viewModel<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        onStartClick()
    }

    private fun onStartClick() {
        binding?.startButton?.setOnClickListener {
            viewModel.connectToServer()
        }
    }
}
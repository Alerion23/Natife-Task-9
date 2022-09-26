package com.example.natifetask9.ui.fragments.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.natifetask9.R
import com.example.natifetask9.databinding.AuthFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : Fragment(R.layout.auth_fragment) {

    private var binding: AuthFragmentBinding? = null
    private val viewModel by viewModel<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AuthFragmentBinding.bind(view)
        onStartClick()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.isConnected.collectLatest {
                    if (it) {
                        findNavController().navigate(R.id.go_to_user_list_fragment)
                    }
                }
            }
            launch {
                viewModel.isLoading.collectLatest {
                    binding?.authProgressBar?.isVisible = it
                }
            }
        }
    }

    private fun onStartClick() {
        binding?.apply {
            startButton.setOnClickListener {
                if (typeUsername.text.isNotEmpty()) {
                    val userName = typeUsername.text.toString()
                    viewModel.connectToServer(userName)
                } else {
                    Toast.makeText(context, getString(R.string.type_your_name), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
package com.example.natifetask9.ui.fragments.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.natifetask9.R
import com.example.natifetask9.databinding.ChatFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChatFragment : Fragment(R.layout.chat_fragment) {

    private var binding: ChatFragmentBinding? = null
    private val args: ChatFragmentArgs by navArgs()
    private val viewModel by viewModel<ChatViewModel> {
        parametersOf(args.otherUserId)
    }
    private val chatAdapter: ChatAdapter by lazy {
        ChatAdapter(args.otherUserId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ChatFragmentBinding.bind(view)
        setupView()
        onSendMessageClick()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.messagesList.collectLatest {
                chatAdapter.submitList(it)
            }
        }
    }

    private fun setupView() {
        binding?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.stackFromEnd = true
            recyclerChat.layoutManager = linearLayoutManager
            recyclerChat.adapter = chatAdapter
            recyclerChat.itemAnimator = null
        }
    }

    private fun onSendMessageClick() {
        binding?.apply {
            buttonSend.setOnClickListener {
                val messageText = editMessage.text.toString()
                if (messageText.isNotEmpty()) {
                    viewModel.sendMessage(messageText)
                    editMessage.setText("")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
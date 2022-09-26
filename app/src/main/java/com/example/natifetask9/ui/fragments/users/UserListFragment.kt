package com.example.natifetask9.ui.fragments.users

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.natifetask9.R
import com.example.natifetask9.databinding.UserListFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment(R.layout.user_list_fragment) {

    private var binding: UserListFragmentBinding? = null
    private val viewModel by viewModel<UserListViewModel>()
    private val userAdapter: UserListAdapter by lazy {
        UserListAdapter(onItemClicked = {})
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = UserListFragmentBinding.bind(view)
        setupView()
        observeViewModel()
        onSwipeRefresh()
    }

    private fun onSwipeRefresh() {
        binding?.swipeUserList?.setOnRefreshListener {
            viewModel.fetchUsers()
        }
    }

    private fun setupView() {
        binding?.apply {
            usersRecycler.layoutManager = LinearLayoutManager(context)
            usersRecycler.adapter = userAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.isLoading.collectLatest {
                    binding?.userListProgressBar?.isVisible = it
                    if (!it) {
                        binding?.swipeUserList?.isRefreshing = it
                    }
                }
            }
            launch {
                viewModel.userList.collectLatest {
                    userAdapter.submitList(it)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
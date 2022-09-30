package com.example.natifetask9.ui.fragments.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.User
import com.example.natifetask9.databinding.UserListBinding

class UserListAdapter(
    private val onItemClicked: (String) -> Unit
) : ListAdapter<User, UserListAdapter.UsersViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = UserListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UsersViewHolder(onItemClicked, binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class UserDiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.name == newItem.name
        }
    }

    class UsersViewHolder(
        private val onItemClicked: (String) -> Unit,
        private val binding: UserListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(currentItem: User) {
            binding.nameOfUsers.text = currentItem.name
            itemView.setOnClickListener {
                onItemClicked(currentItem.id)
            }
        }
    }

}
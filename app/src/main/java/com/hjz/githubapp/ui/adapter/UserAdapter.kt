package com.hjz.githubapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hjz.githubapp.data.response.ItemsItem
import com.hjz.githubapp.databinding.ItemRowUserBinding
import com.hjz.githubapp.ui.DetailUserActivity

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val listUser = ArrayList<ItemsItem>()
    fun setList (users : List<ItemsItem>) {
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users : ItemsItem) {
            with(binding){
                tvName.text = users.login
                Glide.with(itemView)
                    .load(users.avatarUrl)
                    .centerCrop()
                    .into(imgProfile)
                cvItemUser.setOnClickListener {
                    val intent = Intent(it.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USERNAME, users.login)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}
package com.hjz.githubapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hjz.githubapp.data.response.FollowersResponseItem
import com.hjz.githubapp.databinding.ItemRowUserBinding
import com.hjz.githubapp.ui.DetailUserActivity

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private val listFollowers = ArrayList<FollowersResponseItem>()
    fun setList(followers : List<FollowersResponseItem>){
        listFollowers.clear()
        listFollowers.addAll(followers)
        notifyDataSetChanged()
    }

    inner class FollowersViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users : FollowersResponseItem){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersViewHolder(binding)
    }

    override fun getItemCount(): Int = listFollowers.size

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(listFollowers[position])
    }
}
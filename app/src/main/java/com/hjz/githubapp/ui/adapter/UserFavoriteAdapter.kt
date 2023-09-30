package com.hjz.githubapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hjz.githubapp.data.database.entity.UserFavorite
import com.hjz.githubapp.databinding.ItemRowUserBinding
import com.hjz.githubapp.ui.DetailUserActivity

class UserFavoriteAdapter : RecyclerView.Adapter<UserFavoriteAdapter.FavoriteViewHolder>() {

    private val listFavorite = ArrayList<UserFavorite>()
    fun setListFavorit(favorite : List<UserFavorite>){
        listFavorite.clear()
        listFavorite.addAll(favorite)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: UserFavorite){
            with(binding){
                tvName.text = favorite.login
                Glide.with(itemView)
                    .load(favorite.avatar_url)
                    .centerCrop()
                    .into(imgProfile)
                cvItemUser.setOnClickListener {
                    val intent = Intent(it.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USERNAME, favorite.login)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }
}
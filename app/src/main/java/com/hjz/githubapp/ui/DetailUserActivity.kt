package com.hjz.githubapp.ui

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hjz.githubapp.R
import com.hjz.githubapp.data.database.entity.UserFavorite
import com.hjz.githubapp.data.database.room.DbModule
import com.hjz.githubapp.data.model.DetailUserViewModel
import com.hjz.githubapp.data.model.SettingViewModel
import com.hjz.githubapp.data.model.SettingsViewModelFactory
import com.hjz.githubapp.databinding.ActivityDetailUserBinding
import com.hjz.githubapp.ui.adapter.SectionsPagerAdapter
import com.hjz.githubapp.utils.SettingPreferences
import com.hjz.githubapp.utils.dataStore

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel> {
        DetailUserViewModel.Factory(DbModule(this))
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    private var userFavorit: UserFavorite = UserFavorite(id = 0, login = "", avatar_url = "")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val username = intent.getStringExtra(EXTRA_USERNAME)
        var avatarUrl : String? = null
        var id : Int = 0
        var linkUrl : String? = null

        if (username != null) {
            detailUserViewModel.getDetailUser(username)
        }

        detailUserViewModel.users.observe(this){
            binding.apply {
                tvName.text = it.name?:resources.getString(R.string.not_set)
                tvUsername.text = "@${it.login}"
                location.text = it.location?:resources.getString(R.string.not_set)
                blog.text = it.blog?:resources.getString(R.string.not_set)
                tvFollowers.text = "${it.followers} Followers"
                tvFollowing.text = "${it.following} Following"
                Glide.with(this@DetailUserActivity)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(imgDetailProfile)
            }
            avatarUrl = it.avatarUrl
            id = it.id
            Log.d("User", id.toString())
            linkUrl = it.htmlUrl
        }

        detailUserViewModel.isLoading.observe(this){
            showLoading(it)
        }

        // send data to FollowersFragment and FollowingFragment
        val usernameBundle = Bundle()
        usernameBundle.putString(EXTRA_USERNAME, username)

        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, lifecycle, usernameBundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        // check user favorite
        detailUserViewModel.resultInsertFavorite.observe(this){
            //binding.btnFavorite.changeIconColor(R.color.red)
            Toast.makeText(this, "User ${username} di favoritkan", Toast.LENGTH_SHORT).show()
        }
        detailUserViewModel.resultDeleteFavorite.observe(this){
            //binding.btnFavorite.changeIconColor(R.color.white)
            Toast.makeText(this, "User ${username} dihapus dari favorit", Toast.LENGTH_SHORT).show()
        }

        // add to favorite
        binding.btnFavorite.setOnClickListener {
            if (id != null && id != 0) {
                userFavorit.id = id as Int
                userFavorit.login = username ?: ""
                userFavorit.avatar_url = avatarUrl ?: ""
                detailUserViewModel.setFavorite(userFavorit)
            }
        }

        // Inisialisasi status favorit saat halaman dimuat ulang
        detailUserViewModel.findFavorite(id ?: 0) {
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        // AppBar
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_share -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Hi, kunjungi profile ini ya. ${linkUrl}")
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                    true
                }
                else -> false
            }
        }
    }

    fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
        imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}


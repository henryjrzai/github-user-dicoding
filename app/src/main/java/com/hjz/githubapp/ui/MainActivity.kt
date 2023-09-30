package com.hjz.githubapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjz.githubapp.R
import com.hjz.githubapp.data.model.SettingViewModel
import com.hjz.githubapp.data.model.SettingsViewModelFactory
import com.hjz.githubapp.data.model.UserViewModel
import com.hjz.githubapp.databinding.ActivityMainBinding
import com.hjz.githubapp.ui.adapter.UserAdapter
import com.hjz.githubapp.utils.SettingPreferences
import com.hjz.githubapp.utils.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : UserAdapter
    private lateinit var userViewModel : UserViewModel

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showImage(true)

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

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_favorite -> {
                    val intent = Intent(this, UserFavorite::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_setting -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        binding.apply {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    val query = searchView.text.toString()
                    searchUsers(query)
                    searchView.hide()
                    showImage(false)
                    return@setOnEditorActionListener true
                }
                false
            }
        }

        userViewModel.users.observe(this){ users ->
            if (users.isNotEmpty()) {
                adapter.setList(users)
            } else {
                showImage(true)
                binding.imgSearch.setImageDrawable(getDrawable(R.drawable.not_found))
            }
        }

        userViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun searchUsers(query : String) {
        if (query.isNotEmpty()) {
            userViewModel.getSearchUsers(query)
        } else {
            Toast.makeText(this, "username kosong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showImage(isShow: Boolean) {
        if (isShow) {
            binding.imgSearch.visibility = View.VISIBLE
        } else {
            binding.imgSearch.visibility = View.INVISIBLE
        }
    }

}
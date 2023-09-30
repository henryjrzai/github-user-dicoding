package com.hjz.githubapp.ui.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hjz.githubapp.R
import com.hjz.githubapp.ui.FollowersFragment
import com.hjz.githubapp.ui.FollowingFragment


class SectionsPagerAdapter(
    activity: FragmentManager, lifecycle: Lifecycle,
    private val username: Bundle
) : FragmentStateAdapter(activity, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowersFragment()
            }
            1 -> {
                fragment = FollowingFragment()
            }
        }
        fragment?.arguments = username
        return fragment as Fragment
    }

    override fun getItemCount(): Int = 2
}
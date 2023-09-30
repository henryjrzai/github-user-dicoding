package com.hjz.githubapp.ui

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hjz.githubapp.R
import com.hjz.githubapp.data.model.SettingViewModel
import com.hjz.githubapp.utils.SettingPreferences
import com.hjz.githubapp.utils.dataStore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext

        ActivityScenario.launch(MainActivity::class.java)

        val dataStore = context.dataStore
        val settingPreferences = SettingPreferences.getInstance(dataStore)
        val settingViewModel = SettingViewModel(settingPreferences)
        settingViewModel.saveThemeSetting(false) // Set theme to light mode
    }

    @Test
    fun testNavigationToUserFavorite() {
        // Klik menu favorit
        onView(withId(R.id.menu_favorite)).perform(click())

        // Pastikan aktivitas UserFavorite terbuka
        ActivityScenario.launch(UserFavorite::class.java)
    }

    @Test
    fun testNavigationToSettingActivity() {
        // Klik menu pengaturan
        onView(withId(R.id.menu_setting)).perform(click())

        // Pastikan aktivitas SettingActivity terbuka
        ActivityScenario.launch(SettingActivity::class.java)
    }

}
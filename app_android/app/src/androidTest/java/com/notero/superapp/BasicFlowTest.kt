package com.notero.superapp

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.notero.superapp.ui.LoginActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BasicFlowTest {

    @Test
    fun loginScreen_hasEmailAndPasswordFields() {
        ActivityScenario.launch<LoginActivity>(Intent()).use {
            onView(withId(R.id.etEmail)).check(matches(isDisplayed()))
            onView(withId(R.id.etPassword)).check(matches(isDisplayed()))
        }
    }
}

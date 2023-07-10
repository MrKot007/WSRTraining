package com.example.a0sessiontry3

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @JvmField
    @Rule

    val scenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkButtonText() {
        val buttonText = onView(withId(R.id.nextText))
        buttonText.check(matches(withText("Next")))
    }

    @Test
    fun checkButtonChange() {
        val next = onView(withId(R.id.next))
        val signUp = onView(withId(R.id.signUp))
        next.perform(click())
        next.perform(click())
        if (controller.getSize() == 0) {
            signUp.check(matches(isDisplayed()))
        }
    }

    @Test
    fun checkTransferToHolder() {
        val next = onView(withId(R.id.next))
        val signIn = onView(withId(R.id.goToSignIn))
        val holder = onView(withId(R.id.holder))
        next.perform(click())
        next.perform(click())
        signIn.perform(click())
        holder.check(matches(isDisplayed()))
    }

}
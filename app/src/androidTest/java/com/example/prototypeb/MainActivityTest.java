package com.example.prototypeb;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.prototypeb.ui.registration.RegistrationFragment;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest extends TestCase {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);
    private final int SPLASH_SCREEN_DURATION = 5000;
    @Test
    public void testSplash_screen_shown(){
        //check if elements are seen on splash screen
        onView(withId(R.id.splash_logo))
                .check(matches(isDisplayed()));
        onView(withId(R.id.splash_title))
                .check(matches(isDisplayed()));
        onView(withId(R.id.splash_desc))
                .check(matches(isDisplayed()));

        try {
            Thread.sleep(SPLASH_SCREEN_DURATION);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // check if elements are removed from splash screen
        onView(withId(R.id.splash_logo))
                .check((doesNotExist()));
        onView(withId(R.id.splash_title))
                .check((doesNotExist()));
        onView(withId(R.id.splash_desc))
                .check((doesNotExist()));


    }


}
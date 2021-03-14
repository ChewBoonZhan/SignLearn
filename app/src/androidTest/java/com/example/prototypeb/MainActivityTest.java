package com.example.prototypeb;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.ui.registration.RegistrationFragment;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
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
/*
This class test needs to be run when there is no data inside the app. Otherwise, this will cause errors when running
the tests, or might even cause data loss to the app. Improvements might need to be made to allow app data to be retained
when this test is runned on app that have data in it.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest extends TestCase {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);
    private final int SPLASH_SCREEN_DURATION = 5000;
    private Context context;
    private File_connections file_connections;

    @Before
    public void setUp() throws Exception {

        context = ApplicationProvider.getApplicationContext();
        file_connections = new File_connections(context);
    }
    @After
    public void reset_file_contents(){
        file_connections.reset_icon();
        file_connections.reset_name();
    }

    @Test
    public void testGoto_registration_screen(){
        file_connections.reset_name();
        file_connections.reset_icon();
        //file_connection has no user icon and name
        testSplash_screen_shown();
        onView(withId(R.id.register_first_name))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testGoto_main_screen(){
        file_connections.set_user_icon(R.drawable.thor_icon);
        file_connections.set_user_name("Chew Boon Zhan");
        Log.d("Odd Test",file_connections.get_user_name());
        //file_connection has user_icon and name
        testSplash_screen_shown();

        onView(withId(R.id.fragment_action_bar))
                .check(matches(isDisplayed()));

    }



    private void testSplash_screen_shown(){
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
package com.example.prototypeb.ui.translator;

import android.Manifest;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import com.example.prototypeb.MainActivity;
import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.camera.Camera_handle;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.custom_matcher.matcher.Spinner_change_category_test;
import com.example.prototypeb.ui.home.HomeFragment;
import com.example.prototypeb.ui.registration.RegistrationFragment;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.prototypeb.custom_matcher.matcher.View_tests.forceClick;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TranslatorFragmentTest extends TestCase {
    //uses home fragment class
    @Rule
    public ActivityScenarioRule<HomeFragment> activityRule
            = new ActivityScenarioRule<>(HomeFragment.class);

    //requires camera permission
    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.CAMERA");


    @Test
    public void testTranslatorFragment() {

        //go to translator screen
        onView(withId(R.id.navigation_translator))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        //test if switch_camera changes the camera from front to back camera
        testChangeCamera();

        //see if changing categories work
        testChangeCategory();
    }
    private void testChangeCategory(){
        App_data app_data = new App_data();
        Spinner_change_category_test spinner_change_category_test = new Spinner_change_category_test();
        String[] categories = app_data.getCategories();
        int length = categories.length;
        for(int i = 0;i<length;i++){
            //click on spinner
            onView(withId(R.id.category_selector))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            //select a category
            onData(allOf(is(instanceOf(String.class)), is(categories[i]))).perform(click());

            //make sure spinner text has the same text as selected category
            onView(withId(R.id.category_selector)).check(matches(withSpinnerText(containsString(categories[i]))));

            //make sure category is actually changed by looking at label_text
            //set category index
            spinner_change_category_test.set_category_index(i);
            onView(withId(R.id.classitext))
                    .check(matches(spinner_change_category_test));

        }

    }
    private void testChangeCamera(){
        //default it is on front camera
        onView(withId(R.id.camera_type))
                .check(matches(withText(containsString("Front Camera"))));

        //click on switch camera, and check if it is on back camera
        onView(withId(R.id.switch_camera))
                .check(matches(isDisplayed()))
                .perform(forceClick());
        onView(withId(R.id.camera_type))
                .check(matches(withText(containsString("Back Camera"))));

        //click on switch camera, and check if it is on front camera
        onView(withId(R.id.switch_camera))
                .check(matches(isDisplayed()))
                .perform(forceClick());
        onView(withId(R.id.camera_type))
                .check(matches(withText(containsString("Front Camera"))));

    }
  

}
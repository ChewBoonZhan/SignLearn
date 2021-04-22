package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.ui.home.HomeFragment;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.prototypeb.custom_matcher.matcher.View_tests.forceClick;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AdverbsTest extends TestCase {
    //uses home fragment class
    @Rule
    public ActivityScenarioRule<HomeFragment> activityRule
            = new ActivityScenarioRule<>(HomeFragment.class);
    private Context context;
    private File_connections file_connections;
    private App_data app_data;
    private String[] categories;



    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        file_connections = new File_connections(context);
        app_data = new App_data();
        categories = app_data.getCategories();

    }

    @Test
    public void check_category_notifi() {
        // unlock category
        file_connections.unlock_category(categories[0]);

        //go to game screen
        onView(withId(R.id.navigation_game))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        //switch back to translator screen
        onView(withId(R.id.navigation_lesson))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        // go to adverbs
        onView(withId(R.id.lesson1_id))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        onView(withId(R.id.yes_notifi))
                .check(matches(isDisplayed()));

        onView(withId(R.id.no_notifi))
                .check(matches(isDisplayed()));

        onView(withId(R.id.almost_notifi))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.later_notifi))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));


    }


}
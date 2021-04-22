package com.example.prototypeb.ui.lesson;

import android.content.Context;
import android.widget.Button;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.ui.home.HomeFragment;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.prototypeb.custom_matcher.matcher.View_tests.forceClick;
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LessonFragmentTest extends TestCase {
    //uses home fragment class
    @Rule
    public ActivityScenarioRule<HomeFragment> activityRule
            = new ActivityScenarioRule<>(HomeFragment.class);
    private Context context;
    private File_connections file_connections;
    private App_data app_data;
    private String[] categories;
    private ArrayList<Integer> lesson_button;
    private ArrayList<Integer> lesson_menu_collection;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        file_connections = new File_connections(context);
        app_data = new App_data();
        categories = app_data.getCategories();
        init_lesson_button();
        init_lesson_menu();
    }

    private void init_lesson_button(){
        lesson_button = new ArrayList<Integer>();
        lesson_button.add(R.id.lesson1_id);
        lesson_button.add(R.id.lesson2_id);
        lesson_button.add(R.id.lesson3_id);
        lesson_button.add(R.id.lesson4_id);
        lesson_button.add(R.id.lesson5_id);
    }
    private void init_lesson_menu(){
        lesson_menu_collection = new ArrayList<Integer>();
        lesson_menu_collection.add(R.id.adverbs_menu_scrollview);
        lesson_menu_collection.add(R.id.alphabets_menu_scrollview);
        lesson_menu_collection.add(R.id.attachments_menu_scrollview);
        lesson_menu_collection.add(R.id.numbers_menu_scrollview);
        lesson_menu_collection.add(R.id.pronouns_menu_scrollview);
    }
    //Test for locked clicks
    @Test
    public void test_locked_class() {
        //go to translator screen
        onView(withId(R.id.navigation_lesson))
                .check(matches(isDisplayed()))
                .perform(forceClick());


        // lock categories
        int length = categories.length;
        for (int i = 0;i<length;i++){
            file_connections.lock_category(categories[i]);
        }

        // set score to be 0, so that user cannot unlock categories
        file_connections.update_score_file_only(0);

        // check and make sure each button is clicked and nothing happends.
        length =  lesson_button.size();
        for(int i = 0;i<length;i++){
            // click on locked category
            onView(withId(lesson_button.get(i)))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            // make sure user is still in lesson_screen
            onView(withId(R.id.lesson_fragment_grid_layout))
                    .check(matches(isDisplayed()));


            // make sure user is not in lesson menu screen
            onView(withId(lesson_menu_collection.get(i)))
                    .check(doesNotExist());
        }

    }

    @Test
    public void test_locked_sufficient_point(){
        //go to translator screen
        onView(withId(R.id.navigation_lesson))
                .check(matches(isDisplayed()))
                .perform(forceClick());


        // lock categories
        int length = categories.length;
        for (int i = 0;i<length;i++){
            file_connections.lock_category(categories[i]);
        }

        // set score to be 0, so that user cannot unlock categories
        file_connections.update_score_file_only(0);
    }


    //Test for unlocked clicks
    @Test
    public void test_unlocked_class() {
        // unlock categories
        /*
        int length = categories.length;
        for (int i = 0;i<length;i++){
            file_connections.unlock_category(categories[i]);
        }

        // check and make sure each button is clicked and user .
        length =  lesson_button.size();
        for(int i = 0;i<length;i++){
            // click on locked category
            onView(withId(lesson_button.get(i)))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            // make sure user is still in lesson_screen
            onView(withId(R.id.lesson_fragment_scrollview))
                    .check(matches(isDisplayed()));


            // make sure user is not in lesson menu screen
            onView(withId(lesson_menu_collection.get(i)))
                    .check(doesNotExist());
        }

         */
    }

    @After
    public void set_category_locked_back(){

    }
}
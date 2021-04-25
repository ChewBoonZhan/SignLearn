package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.app_data.Category_elements;
import com.example.prototypeb.controller.file_connections.File_connection_key;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.ui.home.HomeFragment;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.provider.Settings.Global.getString;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.prototypeb.custom_matcher.matcher.View_tests.forceClick;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AdverbsTest extends TestCase {
    //uses home fragment class
    @Rule
    public ActivityScenarioRule<HomeFragment> activityRule
            = new ActivityScenarioRule<>(HomeFragment.class);

    //requires camera permission
    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.CAMERA");
    private Context context;
    private File_connections file_connections;
    private App_data app_data;
    private String[] categories;
    private ArrayList <Integer> lesson_title;
    private ArrayList <Integer> lesson_description;
    private int category_syllabus_index = 0;
    private ArrayList <Integer> translator_can_verify_id;
    private ArrayList <Integer> translator_cannot_verify_id;
    private ArrayList <Integer> translator_can_verify_notifi;
    private ArrayList <Integer> translator_cannot_verify_notifi;
    private ArrayList <Integer> category_lesson_button;
    private ArrayList <Integer> category_scrollview;
    private ArrayList <String> translator_label;
    private final int category_index = 0;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        file_connections = new File_connections(context);
        app_data = new App_data();
        categories = app_data.getCategories();


    }

    @Test
    public void check_syllabus() {
        init_translator_can_verify_id();
        init_translator_cannot_verify_id();
        init_translator_can_verify_notifi();
        init_category_lesson_button();
        init_translator_cannot_verify_notifi();
        init_lesson_topics();
        init_lesson_description();
        init_lesson_scrollview();
        init_translator_label();

        // unlock category
        file_connections.unlock_category(categories[category_index]);

        //go to game screen
        onView(withId(R.id.navigation_game))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        //switch back to translator screen
        onView(withId(R.id.navigation_lesson))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        // go to adverbs
        onView(withId(category_lesson_button.get(category_index)))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        int length = translator_can_verify_notifi.size();
        for(int i = 0;i<length;i++){
            onView(withId(translator_can_verify_notifi.get(i)))
                    .check(matches(isDisplayed()));
        }

        length = translator_cannot_verify_notifi.size();
        for (int i = 0;i<length;i++){
            onView(withId(translator_cannot_verify_notifi.get(i)))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        }

        length = translator_can_verify_id.size();
        for(int i = 0;i<length;i++){
            onView(withId(translator_can_verify_id.get(i)))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            // check that user is not on adverbs_menu
            onView(withId(category_scrollview.get(category_index)))
                    .check(doesNotExist());

            // check that user is in lesson_info screen
            onView(withId(R.id.lesson_info_constraint))
                    .check(matches(isDisplayed()));

            // check that text on lesson_screen is in accordance with the syllabus clicked
            onView(withId(R.id.lesson_text_title))
                    .check(matches(withText(context.getString(lesson_title.get(category_syllabus_index)))));

            // check that text on description screen is in accordance to syllabus
            onView(withId(R.id.lesson_info_defcontent))
                    .check(matches(withText(context.getString(lesson_description.get(category_syllabus_index)))));

            category_syllabus_index++;

            // try clicking on check, to checck if user will go to new screen
            onView(withId(R.id.check_translator_info_lesson))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            // check user is not on lesson info screen
            onView(withId(R.id.lesson_info_constraint))
                    .check(doesNotExist());

            // check if user is on translator screen
            onView(withId(R.id.translator_lesson_verify_constraint))
                    .check(matches(isDisplayed()));

            //make sure user is on front camera
            onView(withId(R.id.camera_type_verify))
                    .check(matches(withText("Front Camera")));

            // click to switch camera
            onView(withId(R.id.switch_camera_verify_lesson))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            // make sure user is on back camera
            onView(withId(R.id.camera_type_verify))
                    .check(matches(withText("Back Camera")));

            // press back, and make sure it goes back to lesson info
            onView(withId(R.id.back_button))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            // check user is on lesson info screen
            onView(withId(R.id.lesson_info_constraint))
                    .check(matches(isDisplayed()));

            // check if user is not on translator screen
            onView(withId(R.id.translator_lesson_verify_constraint))
                    .check(doesNotExist());


            // press back, and make sure it goes back to all syllabus
            onView(withId(R.id.back_button))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            // check user is not on lesson info screen
            onView(withId(R.id.lesson_info_constraint))
                    .check(doesNotExist());

            // check user is on syllabus screen
            onView(withId(category_scrollview.get(category_index)))
                    .check(matches(isDisplayed()));

            try{
                Thread.sleep(1000);
            }catch(Exception e){

            }

        }

        length = translator_cannot_verify_id.size();
        for(int i = 0;i<length;i++){
            onView(withId(translator_cannot_verify_id.get(i)))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            // check that user is not on adverbs_menu
            onView(withId(category_scrollview.get(category_index)))
                    .check(doesNotExist());

            // check that user is in lesson_info screen
            onView(withId(R.id.lesson_info_constraint))
                    .check(matches(isDisplayed()));

            // check that text on lesson_screen is in accordance with the syllabus clicked
            onView(withId(R.id.lesson_text_title))
                    .check(matches(withText(context.getString(lesson_title.get(category_syllabus_index)))));

            // check that text on description screen is in accordance to syllabus
            onView(withId(R.id.lesson_info_defcontent))
                    .check(matches(withText(context.getString(lesson_description.get(category_syllabus_index)))));

            category_syllabus_index++;

            // try clicking on check, to checck if user will go to new screen
            onView(withId(R.id.check_translator_info_lesson))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            // click dialog okay
            onView(withText("Okay."))
                    .inRoot(isDialog())
                    .perform(forceClick());

            // check user is on lesson info screen
            onView(withId(R.id.lesson_info_constraint))
                    .check(matches(isDisplayed()));

            // check if user is not on translator screen
            onView(withId(R.id.translator_lesson_verify_constraint))
                    .check(doesNotExist());

            // press back, and make sure it goes back to all syllabus
            onView(withId(R.id.back_button))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            // check user is not on lesson info screen
            onView(withId(R.id.lesson_info_constraint))
                    .check(doesNotExist());

            // check user is on syllabus screen
            onView(withId(category_scrollview.get(category_index)))
                    .check(matches(isDisplayed()));

            try{
                Thread.sleep(1000);
            }catch(Exception e){

            }

        }
        // go back to the lesson main screen
        onView(withId(R.id.back_button))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        // make sure user is on main lesson screen
        onView(withId(R.id.lesson_fragment_scrollview))
                .check(matches(isDisplayed()));

        // make sure user is not on syllabus screen
        onView(withId(category_scrollview.get(category_index)))
                .check(doesNotExist());

        // check to make syllabus learnt, and make sure notification is not showed
        //create new file connection key to save and get data from file connection
        File_connection_key file_connection_key = new File_connection_key();
        length = translator_label.size();
        for(int i = 0;i<length;i++){

            String syllabus_learnt = translator_label.get(i).toLowerCase() + file_connection_key.getLesson_passed_back_key();

            // set such that the syllabus of category is learnt
            file_connections.syllabus_learnt(syllabus_learnt);
        }

        // go to adverbs screen again
        onView(withId(category_lesson_button.get(category_index)))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        // make sure no notifi icon is shown
        length = translator_can_verify_notifi.size();
        for(int i = 0;i<length;i++){
            onView(withId(translator_can_verify_notifi.get(i)))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        }

        length = translator_cannot_verify_notifi.size();
        for(int i = 0;i<length;i++){
            onView(withId(translator_cannot_verify_notifi.get(i)))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        }

    }
    private void init_translator_can_verify_id(){
        translator_can_verify_id = new ArrayList<Integer>();
        translator_can_verify_id.add(R.id.yes_id);
        translator_can_verify_id.add(R.id.no_id);

    }
    private void init_translator_cannot_verify_id(){
        translator_cannot_verify_id = new ArrayList<Integer>();
        translator_cannot_verify_id.add(R.id.almost_id);
        translator_cannot_verify_id.add(R.id.later_id);

    }
    private void init_translator_can_verify_notifi(){
        translator_can_verify_notifi = new ArrayList<Integer>();
        translator_can_verify_notifi.add(R.id.yes_notifi);
        translator_can_verify_notifi.add(R.id.no_notifi);
    }
    private void init_translator_cannot_verify_notifi(){
        translator_cannot_verify_notifi = new ArrayList<Integer>();
        translator_cannot_verify_notifi.add(R.id.almost_notifi);
        translator_cannot_verify_notifi.add(R.id.later_notifi);
    }
    private void init_lesson_topics(){
        lesson_title = new ArrayList<Integer>();
        lesson_title.add(R.string.yes_title);
        lesson_title.add(R.string.no_title);
        lesson_title.add(R.string.almost_title);
        lesson_title.add(R.string.later_title);

    }private void init_lesson_description(){
        lesson_description = new ArrayList<Integer>();
        lesson_description.add(R.string.yes_content);
        lesson_description.add(R.string.no_content);
        lesson_description.add(R.string.almost_content);
        lesson_description.add(R.string.later_content);

    }
    private void init_translator_label(){
        Category_elements category_elements = new Category_elements();
        translator_label = category_elements.get_category_elements_testable(category_index);

    }
    private void init_category_lesson_button(){
        category_lesson_button = new ArrayList<Integer>();
        category_lesson_button.add(R.id.lesson1_id);
        category_lesson_button.add(R.id.lesson2_id);
        category_lesson_button.add(R.id.lesson3_id);
        category_lesson_button.add(R.id.lesson4_id);
        category_lesson_button.add(R.id.lesson5_id);
    }
    private void init_lesson_scrollview(){
        category_scrollview = new ArrayList<Integer>();
        category_scrollview.add(R.id.adverbs_menu_scrollview);
        category_scrollview.add(R.id.alphabets_menu_scrollview);
        category_scrollview.add(R.id.attachments_menu_scrollview);
        category_scrollview.add(R.id.numbers_menu_scrollview);
        category_scrollview.add(R.id.pronouns_menu_scrollview);
    }
    @After
    public void set_category_syllabus_unlocked(){
        // unlock category 1, by default
        file_connections.unlock_category(categories[category_index]);
        
        //create new file connection key to save and get data from file connection
        File_connection_key file_connection_key = new File_connection_key();
        int length = translator_label.size();
        for(int i = 0;i<length;i++){

            String syllabus_learnt = translator_label.get(i).toLowerCase() + file_connection_key.getLesson_passed_back_key();

            // set such that the syllabus of category is not learnt
            file_connections.syllabus_not_learnt(syllabus_learnt);
        }

    }


}
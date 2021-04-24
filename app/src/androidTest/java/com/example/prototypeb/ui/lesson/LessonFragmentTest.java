package com.example.prototypeb.ui.lesson;

import android.content.Context;
import android.widget.Button;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

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

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.prototypeb.custom_matcher.matcher.View_tests.forceClick;
import static com.example.prototypeb.custom_matcher.matcher.View_tests.testBackgroundColour;

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
    private ArrayList<Integer> lesson_notifi_collection;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        file_connections = new File_connections(context);
        app_data = new App_data();
        categories = app_data.getCategories();
        init_lesson_button();
        init_lesson_menu();
        init_lesson_notifi();
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
    private void init_lesson_notifi(){
        lesson_notifi_collection = new ArrayList<Integer>();
        lesson_notifi_collection.add(R.id.lesson_1_notifi);
        lesson_notifi_collection.add(R.id.lesson_2_notifi);
        lesson_notifi_collection.add(R.id.lesson_3_notifi);
        lesson_notifi_collection.add(R.id.lesson_4_notifi);
        lesson_notifi_collection.add(R.id.lesson_5_notifi);

    }
    //Test for locked clicks
    @Test
    public void test_locked_class() {
        // lock categories
        int length = categories.length;
        for (int i = 0;i<length;i++){
            file_connections.lock_category(categories[i]);
        }

        //go to game screen
        onView(withId(R.id.navigation_game))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        //switch back to translator screen
        onView(withId(R.id.navigation_lesson))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        int lesson_locked_color = R.color.warning;


        // set score to be 0, so that user cannot unlock categories
        file_connections.update_score_file_only(0);

        // check and make sure each button is clicked and nothing happends.
        length =  lesson_button.size();
        for(int i = 0;i<length;i++){
            // check that notifi icon cannot be seen
            onView(withId(lesson_notifi_collection.get(i)))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

            // click on locked category
            onView(withId(lesson_button.get(i)))
                    .check(matches(isDisplayed()))
                    .check(matches(testBackgroundColour(lesson_locked_color)))
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
        // lock categories
        int length = categories.length;
        for (int i = 0;i<length;i++){
            file_connections.lock_category(categories[i]);
        }

        //go to game screen
        onView(withId(R.id.navigation_game))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        //switch back to translator screen
        onView(withId(R.id.navigation_lesson))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        int lesson_locked_color = R.color.warning;
        int lesson_unlocked_color = R.color.primary;


        // set score to be 0, so that user cannot unlock categories
        file_connections.update_score_file_only(1000);


        // check and make sure each button is clicked and nothing happends.
        length =  lesson_button.size();
        for(int i = 0;i<length;i++){
            // check that notifi icon cannot be seen
            onView(withId(lesson_notifi_collection.get(i)))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

            // click on locked category
            onView(withId(lesson_button.get(i)))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());


            //make sure press no will cause it to go back, and not unlock the category
            onView(withText("No"))
                    .inRoot(isDialog())
                    .perform(forceClick());


            // make sure user is still in lesson_screen
            onView(withId(R.id.lesson_fragment_grid_layout))
                    .check(matches(isDisplayed()));

            // make sure lesson is still locked
            onView(withId(lesson_button.get(i)))
                    .check(matches(isDisplayed()))
                    .check(matches(testBackgroundColour(lesson_locked_color)));

            // make sure notifi button is not shown to user
            onView(withId(lesson_notifi_collection.get(i)))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

            // click on locked category
            onView(withId(lesson_button.get(i)))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());


            // make sure press yes will cause it to unlock the category
            onView(withText("Yes"))
                .inRoot(isDialog())
                .perform(forceClick());

            // make sure lesson is unlocked
            onView(withId(lesson_button.get(i)))
                    .check(matches(isDisplayed()))
                    .check(matches(testBackgroundColour(lesson_unlocked_color)));

            // make sure notifi button is shown to user
            onView(withId(lesson_notifi_collection.get(i)))
                    .check(matches(isDisplayed()));

            // check pressing on unlocked category will lead to new category menu screen

            // click on unlocked category
            onView(withId(lesson_button.get(i)))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            // make sure user is not in lesson_screen
            onView(withId(R.id.lesson_fragment_grid_layout))
                    .check(doesNotExist());


            // make sure user is in lesson menu screen
            onView(withId(lesson_menu_collection.get(i)))
                    .check(matches(isDisplayed()));


            // go back to the previous screen
            onView(withId(R.id.back_button))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            // make sure user is in lesson_screen
            onView(withId(R.id.lesson_fragment_grid_layout))
                    .check(matches(isDisplayed()));
        }


        // test notifi_text when syllabus is learnt
        test_notifi_text();
    }

    private void test_notifi_text(){
        Category_elements category_elements = new Category_elements();
        File_connection_key file_connection_key = new File_connection_key();
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();
        int length = categories.length;
        for(int i = 0;i<length;i++){
            ArrayList <String> syllabus_elements = category_elements.get_category_elements_testable(i);

            int number_of_syllabus = syllabus_elements.size();

            int number_of_unlearnt =number_of_syllabus;

            // make sure the initial text is the same as number of unlearnt categories.
            onView(withId(lesson_notifi_collection.get(i)))
                    .check(matches(withText("" + number_of_unlearnt)));

            for(int j = 0;j<number_of_syllabus;j++){
                //slowly make categories learnt
                String syllabus_learnt = syllabus_elements.get(j).toLowerCase() + file_connection_key.getLesson_passed_back_key();

                // set such that the syllabus of category is learnt
                file_connections.syllabus_learnt(syllabus_learnt);

                number_of_unlearnt--;

                //go to game screen
                onView(withId(R.id.navigation_game))
                        .check(matches(isDisplayed()))
                        .perform(forceClick());

                //switch back to translator screen
                onView(withId(R.id.navigation_lesson))
                        .check(matches(isDisplayed()))
                        .perform(forceClick());

                if(number_of_unlearnt == 0){
                    // check that lesson_notifi is gone
                    onView(withId(lesson_notifi_collection.get(i)))
                            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
                }
                else{
                    // check if number of syllabus unlearnt has reduced
                    onView(withId(lesson_notifi_collection.get(i)))
                            .check(matches(withText("" + number_of_unlearnt)));
                }


            }

        }

    }
    @After
    public void set_category_locked_back(){
        //reset to default score
        file_connections.update_score_file_only(file_connections.getDefault_score_value());

        // save default unlocked and locked categories in file
        file_connections.save_default_category_unlock_in_file();

        // make all syllabus unlearnt
        Category_elements category_elements = new Category_elements();
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();
        File_connection_key file_connection_key = new File_connection_key();
        int length = categories.length;

        for(int i = 0;i<length;i++) {
            ArrayList<String> syllabus_elements = category_elements.get_category_elements_testable(i);

            int number_of_syllabus = syllabus_elements.size();

            for(int j = 0;j<number_of_syllabus;j++) {
                //slowly make categories not learnt
                String syllabus_learnt = syllabus_elements.get(j).toLowerCase() + file_connection_key.getLesson_passed_back_key();

                // set such that the syllabus of category is not learnt
                file_connections.syllabus_not_learnt(syllabus_learnt);

            }

        }
    }
}
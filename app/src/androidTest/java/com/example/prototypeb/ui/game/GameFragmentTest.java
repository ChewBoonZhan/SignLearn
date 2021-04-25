package com.example.prototypeb.ui.game;

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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.prototypeb.custom_matcher.matcher.View_tests.forceClick;
import static com.example.prototypeb.custom_matcher.matcher.View_tests.testBackgroundColour;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GameFragmentTest extends TestCase {
    @Rule
    public ActivityScenarioRule<HomeFragment> activityRule
            = new ActivityScenarioRule<>(HomeFragment.class);
    private Context context;
    private File_connections file_connections;
    private App_data app_data;
    private String[] categories;
    private Integer points;
    private ArrayList<Integer> game_button;
    private ArrayList<Integer> game_notifi;

    @Before
    public void setUp() throws Exception{
        context = ApplicationProvider.getApplicationContext();
        file_connections = new File_connections(context);
        app_data = new App_data();
        categories = app_data.getCategories();
        init_game_buttons();
        init_noti_button();
    }

    private void init_game_buttons(){
        game_button = new ArrayList<Integer>();
        game_button.add(R.id.game1_button_id);
        game_button.add(R.id.game2_button_id);
        game_button.add(R.id.game3_button_id);
        game_button.add(R.id.game4_button_id);
        game_button.add(R.id.game5_button_id);
    }

    private void init_noti_button(){
        game_notifi = new ArrayList<Integer>();
        game_notifi.add(R.id.game_1_notifi);
        game_notifi.add(R.id.game_2_notifi);
        game_notifi.add(R.id.game_3_notifi);
        game_notifi.add(R.id.game_4_notifi);
        game_notifi.add(R.id.game_5_notifi);
    }

    @Test
    public void test_GameButtonDisplay() {
        int length = categories.length;

        //go to Game Fragment screen
        onView(withId(R.id.navigation_game))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        for (int i = 0; i < length; i++) {
            //check game button is displayed
            onView(withId(game_button.get(i)))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void test_locked_cat(){
        int length = categories.length;

        //lock each game category
        for (int i = 0; i<length; i++){
            file_connections.lock_category(categories[i]);
        }

        int game_locked_color = R.color.warning;

        //go to Game Fragment screen
        onView(withId(R.id.navigation_game))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        length = game_button.size();
        for(int i=0; i<length; i++){
            //check notification icon cannot be seen on game buttons
            onView(withId(game_notifi.get(i)))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

            //check if Game Button is orange colour(locked)
            onView(withId(game_button.get(i)))
                    .check(matches(testBackgroundColour(game_locked_color)));

            //make sure user is on Game Fragment screen
            onView(withId(R.id.game_fragment_grid_layout))
                    .check(matches(isDisplayed()));
        }

    }

    @Test
    public void test_unlocked_cat(){
        int length = categories.length;

        //unlock each category
        for(int i=0; i<length; i++) {
            file_connections.unlock_category(categories[i]);
        }

        int game_unlocked_color = R.color.primary;

        //go to Game Fragment Screen
        onView(withId(R.id.navigation_game))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        //make sure user is on Game Fragment screen
        onView(withId(R.id.game_fragment_grid_layout))
                .check(matches(isDisplayed()));

        length = game_button.size();
        for(int i=0; i<length; i++) {
            //check if notifications icon is visible
            onView(withId(game_notifi.get(i)))
                    .check(matches(isDisplayed()));

            //click on each game button
            onView(withId(game_button.get(i)))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());


            //check if game screen is displayed
            onView(withId(R.id.imageQuestion))
                    .check(matches(isDisplayed()));

            //click on back button in game screen
            onView(withId(R.id.back_button))
                    .check(matches(isDisplayed()))
                    .perform(forceClick());

            //make sure user is on Game Fragment screen
            onView(withId(R.id.game_fragment_grid_layout))
                    .check(matches(isDisplayed()));
        }
        test_notifiDisplay();
    }

    private void test_notifiDisplay(){

        Category_elements category_elements = new Category_elements();
        App_data app_data = new App_data();
        String [] categories = app_data.getCategories();
        int notpassed_noti = 1;
        int length = categories.length;

        //Test for pass
        for(int i=0; i<length; i++) {
            ArrayList<String> syllabus_elements = category_elements.getCategory_elements().get(categories[i]);
            //set categories as passed

            int number_of_syllabus = syllabus_elements.size();

            for(int j = 0;j<number_of_syllabus;j++){
                file_connections.set_game_category_passed(syllabus_elements.get(j));
            }

        }

        //REFRESH TO UPDATE CHANGES
        //Go to lesson screen
        onView(withId(R.id.navigation_lesson))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        //Switch back to game screen to see changes
        onView(withId(R.id.navigation_game))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        int buttonlength = game_button.size();
        for(int i=0; i<buttonlength; i++) {
            onView(withId(game_notifi.get(i)))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        }

        //Test for not passed
        for(int i=0; i<length; i++) {
            ArrayList<String> syllabus_elements = category_elements.getCategory_elements().get(categories[i]);
            //set categories as not passed
            int number_of_syllabus = syllabus_elements.size();

            for(int j = 0;j<number_of_syllabus;j++){
                file_connections.set_game_category_not_passed(syllabus_elements.get(j));
            }
        }

        //REFRESH TO UPDATE CHANGES
        //Go to lesson screen
        onView(withId(R.id.navigation_lesson))
                .check(matches(isDisplayed()))
                .perform(forceClick());

        //Switch back to game screen to see changes
        onView(withId(R.id.navigation_game))
                .check(matches(isDisplayed()))
                .perform(forceClick());


        for(int i=0; i<buttonlength; i++) {
            onView(withId(game_notifi.get(i)))
                    .check(matches(withText("" + notpassed_noti)));
            }

    }

    @After
    public void resetDefault(){
        //reset to default score
        file_connections.update_score_file_only(file_connections.getDefault_score_value());

        //save default progress in file
        file_connections.save_default_category_unlock_in_file();

        //set all categories as not passed
        Category_elements category_elements = new Category_elements();
        App_data app_data = new App_data();
        String [] categories = app_data.getCategories();

        int length = categories.length;
        for(int i=0; i<length; i++) {
            ArrayList<String> syllabus_elements = category_elements.getCategory_elements().get(categories[i]);
            //set categories as not passed
            int number_of_syllabus = syllabus_elements.size();

            for(int j = 0;j<number_of_syllabus;j++){
                file_connections.set_game_category_not_passed(syllabus_elements.get(j));
            }
        }
    }
}

package com.example.prototypeb.ui.registration;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.file_connections.File_connections;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static com.example.prototypeb.custom_matcher.matcher.Edit_text_tests.hasNoErrorText;
import static com.example.prototypeb.custom_matcher.matcher.View_tests.forceClick;
import static com.example.prototypeb.custom_matcher.matcher.View_tests.testBackgroundColour;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.AdditionalMatchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegistrationFragmentTest extends TestCase {
    @Rule
    public ActivityScenarioRule<RegistrationFragment> activityRule
            = new ActivityScenarioRule<>(RegistrationFragment.class);
    private String name = "Chew Boon Zhan";
    private Context context;
    private File_connections file_connections;
    private ArrayList<Integer> user_icons;
    private ArrayList <Integer> user_icons_drawable;
    private int selected_drawable;
    @Before
    public void setUp() throws Exception {

        context = ApplicationProvider.getApplicationContext();
        file_connections = new File_connections(context);
        init_user_icons();
        init_user_icons_drawable();

    }
    private void init_user_icons_drawable(){
        user_icons_drawable= new ArrayList<Integer>();
        user_icons_drawable.add(R.drawable.male_icon);
        user_icons_drawable.add(R.drawable.female_icon);
        user_icons_drawable.add(R.drawable.bear_icon);
        user_icons_drawable.add(R.drawable.fish_icon);
        user_icons_drawable.add(R.drawable.thor_icon);
        user_icons_drawable.add(R.drawable.iron_man_icon);
        selected_drawable = user_icons.get(0);
    }
    private void init_user_icons(){
        user_icons= new ArrayList<Integer>();
        user_icons.add(R.id.male_icon);
        user_icons.add(R.id.female_icon);
        user_icons.add(R.id.bear_icon);
        user_icons.add(R.id.fish_icon);
        user_icons.add(R.id.thor_icon);
        user_icons.add(R.id.ironman_icon);


    }
    @After
    public void reset_name_and_score(){
        file_connections.reset_icon();
        file_connections.reset_name();
    }

    @Test
    public void testInvalid_first_name() {
        test_name(true);
        test_name(false);
        test_icon();
        test_file_connection_saved();

    }


    private void test_file_connection_saved(){
		press_go_button(R.id.register_last_name,false);
		assertEquals(file_connections.get_user_icon(),selected_drawable);
		assertEquals(file_connections.get_user_name(),name);
    }

    private void test_icon(){
        int clicked_score_color = R.color.primary;
        int length = user_icons.size();
        for(int i = 0;i<length;i++){
            int user_icon_here = user_icons.get(i);
            onView(withId(user_icon_here))
                    .check(matches(isDisplayed()))
                    .perform(scrollTo(),forceClick());
            onView(withId(user_icon_here))
                    .check(matches(testBackgroundColour(clicked_score_color)))
                    .check(matches(isDisplayed()));
            selected_drawable = user_icons_drawable.get(i);

            //check that the other icons are no colour as it is not selected. or the icon before it
            //TODODO
        }
    }
    private void press_go_button(int edit_text_id,boolean is_first_name){
		int begin_name_index = 5;
		int end_name_index = name.length();
		if(is_first_name){
			begin_name_index = 0;
            end_name_index = 4;
		}
        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .perform(clearText(),typeText(name.substring(begin_name_index,end_name_index)),closeSoftKeyboard());
        onView(withId(R.id.register_complete))
                .check(matches(isDisplayed()))
                .perform(forceClick());
        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .check(matches(hasNoErrorText()));
    }
    private void test_name(boolean is_first_name){
        int min_length = 2;
        int max_length = 10;
        int edit_text_id = R.id.register_last_name;

        String hint = "Last Name";
        if(is_first_name){
            max_length = 5;
            hint = "First Name";
            edit_text_id = R.id.register_first_name;


        }

        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .perform(clearText(),typeText(name.charAt(0) + ""),closeSoftKeyboard());
        onView(withId(R.id.register_complete))
                .check(matches(isDisplayed()))
                .perform(forceClick());
        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .check(matches(hasErrorText(hint +  " must be longer than "+min_length + " characters.")));

        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .perform(clearText(),typeText("  a "),closeSoftKeyboard());
        onView(withId(R.id.register_complete))
                .check(matches(isDisplayed()))
                .perform(forceClick());
        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .check(matches(hasErrorText(hint +  " must be longer than "+min_length + " characters.")));


        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .perform(clearText(),typeText(""),closeSoftKeyboard());
        onView(withId(R.id.register_complete))
                .check(matches(isDisplayed()))
                .perform(forceClick());
        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .check(matches(hasErrorText(hint +  " cannot be empty.")));

        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .perform(clearText(),typeText("   "),closeSoftKeyboard());
        onView(withId(R.id.register_complete))
                .check(matches(isDisplayed()))
                .perform(forceClick());
        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .check(matches(hasErrorText(hint +  " cannot be empty.")));


        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .perform(clearText(),typeText(name),closeSoftKeyboard());
        onView(withId(R.id.register_complete))
                .check(matches(isDisplayed()))
                .perform(forceClick());
        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .check(matches(hasErrorText(hint +  " must be shorter than "+max_length + " characters.")));

        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .perform(clearText(),typeText("!@#$%"),closeSoftKeyboard());
        onView(withId(R.id.register_complete))
                .check(matches(isDisplayed()))
                .perform(forceClick());
        onView(withId(edit_text_id))
                .check(matches(isDisplayed()))
                .check(matches(hasErrorText(hint +  " must only contain Characters and Spaces.")));


        if(is_first_name){
			press_go_button(edit_text_id,is_first_name);
		}



    }




}
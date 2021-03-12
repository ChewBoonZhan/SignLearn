package com.example.prototypeb.controller.file_connections;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.prototypeb.MainActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.app_data.Category_elements;


import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;

import java.util.ArrayList;

public class File_connectionsTest extends TestCase {
    /*
    Please make sure to clear data for the app before running the test cases, as if the app is runned before,
    states of file connection might change, causing the tests to fail as different datas might be saved to the app already.
    All data saved to the file_connection in this test must be cleared to prevent errors.
    create a method reset_state in file connection if required to reset the state of set datas.
     */
    @Rule
    public ActivityScenarioRule mActivityRule =
            new ActivityScenarioRule( MainActivity.class);
    private Context context;
    private File_connections file_connections;

    @Before
    public void setUp() throws Exception {
        /*
        ActivityScenario activity = mActivityRule.getScenario();
        activity.onActivity(activity1 -> {
            file_connections = new File_connections(activity1);

        });
        */
        //waitFor(activity_here::hasWindowFocus);
        context = ApplicationProvider.getApplicationContext();
        file_connections = new File_connections(context);
    }
    
    public void testUnlock_category() {
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();
        String category = categories[0];
        file_connections.unlock_category(category);
        assertEquals(file_connections.is_category_unlocked(category),true);
        file_connections.lock_category(category);


    }
    public void testLock_category(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();
        String category = categories[0];
        file_connections.unlock_category(category);

        file_connections.lock_category(category);
        assertEquals(file_connections.is_category_unlocked(category),false);
    }
    public void testIs_lesson_passed(){

    }
    public void testIs_category_unlocked(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();
        String category = categories[1];
        boolean default_category_value = false;
        assertEquals(file_connections.is_category_unlocked(category),default_category_value);

    }
    public void testSet_user_name() {

        String user_name= "Destiny James";
        file_connections.set_user_name(user_name);
        assertEquals(file_connections.get_user_name(),user_name);
        file_connections.reset_name();
    }


    public void testSet_user_icon() {
        int user_icon = 0;
        try {
            //trying with an actual icon in the file
            user_icon = R.drawable.bear_icon;
        } catch (Exception e) {
            e.printStackTrace();
        }
        file_connections.set_user_icon(user_icon);
        assertEquals(file_connections.get_user_icon(),user_icon);
        file_connections.reset_icon();

    }



    public void testUpdate_score() {
        int score = 10;
        file_connections.update_score(score);
        assertEquals(file_connections.getScore(),score);
        file_connections.reset_score();
    }


    public void testGetDefault_user_icon_value(){
        assertEquals(file_connections.getDefault_user_icon_value(),0);
    }
    public void testGetDefault_score_value(){
        assertEquals(file_connections.getDefault_score_value(),50);
    }
    public void testGetDefault_user_name(){
        assertEquals(file_connections.getDefault_user_name(),"");
    }


    public void testGetScore(){
        file_connections.reset_score();
        assertEquals(file_connections.getScore(),file_connections.getDefault_score_value());
    }
    public void testGet_user_name(){
        file_connections.reset_name();
        assertEquals(file_connections.get_user_name(),file_connections.getDefault_user_name());
    }
    public void testGet_user_icon(){
        file_connections.reset_icon();
        assertEquals(file_connections.get_user_icon(),file_connections.getDefault_user_icon_value());
    }

    public void testReset_name(){
        String default_name = file_connections.getDefault_user_name();
        file_connections.set_user_name("Destiny James");
        file_connections.reset_name();
        assertEquals(file_connections.get_user_name(),default_name);
    }

    public void testReset_score(){
        int default_score = file_connections.getDefault_score_value();
        file_connections.update_score(100);
        file_connections.reset_score();
        assertEquals(file_connections.getScore(),default_score);
    }

    public void testReset_icon(){
        int default_icon = file_connections.getDefault_user_icon_value();
        file_connections.set_user_icon(R.drawable.bear_icon);
        file_connections.reset_icon();
        assertEquals(file_connections.get_user_icon(),default_icon);
    }

    public void testCheck_lesson_learnt(){
        boolean default_value = false;
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();
        Category_elements all_category_elements = new Category_elements();
        ArrayList<String> category_elements = all_category_elements.getCategory_elements().get(categories[0]);
        assertEquals(file_connections.check_lesson_learnt(category_elements.get(0)),default_value);
    }

}
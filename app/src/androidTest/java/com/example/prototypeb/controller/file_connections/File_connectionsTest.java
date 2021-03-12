package com.example.prototypeb.controller.file_connections;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.prototypeb.MainActivity;
import com.example.prototypeb.R;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;

public class File_connectionsTest extends TestCase {
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

    }

    public void testSave_lesson_not_scored_in_file() {
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
        assertEquals(file_connections.getScore(),file_connections.getDefault_score_value());
    }
    public void testGet_user_name(){
        assertEquals(file_connections.get_user_name(),file_connections.getDefault_user_name());
    }
    public void testGet_user_icon(){
        assertEquals(file_connections.get_user_icon(),file_connections.getDefault_user_icon_value());
    }

    public void testGetSharedPref() {
    }
}
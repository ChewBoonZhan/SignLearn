package com.example.prototypeb.controller.file_connections;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.prototypeb.MainActivity;

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

        ActivityScenario activity = mActivityRule.getScenario();
        activity.onActivity(activity1 -> {
            context = activity1;

        });


    }
    public void testUnlock_category() {
    }

    public void testSave_lesson_not_scored_in_file() {
    }

    public void testSet_user_name() {
    }

    public void testSet_user_icon() {
    }

    public void testUpdate_score() {
        int score = 10;
        file_connections = new File_connections(context);
        file_connections.update_score(score);
        assertEquals(file_connections.getScore(),score);

    }

    public void testGetScore() {
    }

    public void testGet_user_name() {
    }

    public void testGet_user_icon() {
    }

    public void testGetSharedPref() {
    }
}
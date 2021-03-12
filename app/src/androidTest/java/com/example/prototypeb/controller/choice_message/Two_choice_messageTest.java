    package com.example.prototypeb.controller.choice_message;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Process;


import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
//import com.android.compatibility.common.util.PollingCheck;
import com.example.prototypeb.MainActivity;

import org.mockito.ArgumentCaptor;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class Two_choice_messageTest extends TestCase {
    private Instrumentation mInstrumentation;


    @Rule
    public ActivityScenarioRule mActivityRule =
            new ActivityScenarioRule( MainActivity.class);
    private Context mContext;
    private Activity activity_here;
    private boolean clicked = false;
    private Two_choice_message two_choice_message;
    private DialogInterface.OnMultiChoiceClickListener mOnMultiChoiceClickListener =
            mock(DialogInterface.OnMultiChoiceClickListener.class);
    @Before
    public void setUp() throws Exception {
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
        ActivityScenario activity = mActivityRule.getScenario();
        activity.onActivity(activity1 -> {
            mContext = activity1;
            activity_here = activity1;
        });

        //waitFor(activity_here::hasWindowFocus);
    }
    public void testShow_message() throws Throwable{

        String message_title = "message_title";
        String message_contents = "message_contents";
        String positive_choice_string = "yes";
        String negative_choice_string = "no";
        clicked = false;
        DialogInterface.OnClickListener positive_choice= new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
                clicked = true;
            }
        };
        DialogInterface.OnClickListener negative_choice  = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
                clicked = true;
            }
        };
        runOnUiThread(new Runnable() {
            public void run() {
                two_choice_message = new Two_choice_message(mContext,message_title,message_contents,positive_choice_string,negative_choice_string,positive_choice,negative_choice);
                two_choice_message.show_message();
            }
        });
        AlertDialog.Builder alert_dialog = two_choice_message.getAlert_dialog();


    }


}
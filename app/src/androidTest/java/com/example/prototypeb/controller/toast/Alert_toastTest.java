package com.example.prototypeb.controller.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.prototypeb.MainActivity;
import com.example.prototypeb.R;
import com.example.prototypeb.controller.file_connections.File_connections;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;

public class Alert_toastTest extends TestCase {
    @Rule
    private Context context;
    private Alert_toast alert_toast;
    ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

    @Before
    public void setUp() throws Exception {

        context = ApplicationProvider.getApplicationContext();
        alert_toast = new Alert_toast(context,"Alert Toast Test Message");
    }

    public void testShow_toast() {

        Thread thread = new Thread(){
            public void run(){
                Looper.prepare();//Call looper.prepare()

                Handler mHandler = new Handler() {
                    public void handleMessage(Message msg) {
                        alert_toast.show_toast();
                        assertEquals(alert_toast.getToast_shown(),true);
                        Toast toast = alert_toast.getToast();
                        View view = toast.getView();
                        TextView text = view.findViewById(android.R.id.message);
                        assertEquals(text.getGravity(), Gravity.CENTER);
                        assertEquals(true, false);
                    }
                };

                Looper.loop();
            }
        };
        thread.start();

        alert_toast.show_toast();


    }

}
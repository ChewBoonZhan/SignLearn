package com.example.prototypeb.controller.new_screen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototypeb.MainActivity;
import com.example.prototypeb.ui.home.HomeFragment;
import com.example.prototypeb.ui.registration.RegistrationFragment;

public class New_screen {

    // set the delay before the next screen is showned to user
    private int delay_before_new_screen;

    //context for opening new screen
    private Context context;

    //activity for opening new screen
    private Activity activity;

    /**
     * Constructor
     * @param delay_before_new_screen - delay before next new screen is displayed to user
     * @param context - context for opening new screen
     * @param activity - activity for opening new screen
     */
    public New_screen(int delay_before_new_screen, Context context,Activity activity){
        this.delay_before_new_screen = delay_before_new_screen;
        this.context = context;
        this.activity = activity;
    }

    /**
     *
     * @param screen_name - name of the screen to go to. The name represents a class of the screen to go to
     * @param clear_screen - determines if the screen history needs to be cleared to prevent back button from going back to the previous screen
     */
    public void go_to_new_screen(String screen_name,boolean clear_screen){
        //set a delay before run will be called to go to new screen
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                //creating new intent to go to new screen
                Intent intent = null;
                try {

                    //try going to new class by the class's string
                    intent = new Intent(context,Class.forName(screen_name));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //start going to the new screen
                context.startActivity(intent);

                if(clear_screen){
                    //clear screen, clear history so that bacck button wont go back to previous screen
                    activity.finish();
                }

            }
        },this.delay_before_new_screen);
    }

}

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
    private int delay_before_new_screen;
    private Context context;
    private Activity activity;
    public New_screen(int delay_before_new_screen, Context context,Activity activity){
        this.delay_before_new_screen = delay_before_new_screen;
        this.context = context;
        this.activity = activity;
    }
    public void go_to_new_screen(String screen_name){
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {

                Intent intent = null;
                try {
                    intent = new Intent(context,Class.forName(screen_name));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                context.startActivity(intent);
                activity.finish();
            }
        },this.delay_before_new_screen);
    }

}

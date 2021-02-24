package com.example.prototypeb.controller.loading_screen;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prototypeb.MainActivity;

import java.util.TimerTask;

public class Loading_screen_task extends TimerTask {
    private ConstraintLayout main_layout;
    private TextView loading_text;
    private ImageView loading_logo_image;

    private MainActivity mainActivity;
    public Loading_screen_task(ConstraintLayout layout, TextView loading_text, ImageView logo, MainActivity mainActivity){
        main_layout = layout;
        this.loading_text = loading_text;
        logo = loading_logo_image;
        this.mainActivity = mainActivity;
    }
    @Override
    public void run() {
        //mainActivity.done_loading_screen();
    }

}

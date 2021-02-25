package com.example.prototypeb.controller.loading_screen;

import android.content.Context;
import android.graphics.Color;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prototypeb.MainActivity;
import com.example.prototypeb.controller.file_connections.File_connections;

import java.util.Timer;
import java.util.TimerTask;

public class Loading_screen  {
    private Loading_screen_task loading_screen_task;
    private Timer timer;

    public Loading_screen(ConstraintLayout layout, TextView loading_text, ImageView logo, MainActivity mainActivity){
        timer = new Timer();

        loading_screen_task = new Loading_screen_task(layout,loading_text,logo,mainActivity);
    }
    public void start_timer(){

        timer.schedule(loading_screen_task,6000);
    }




}

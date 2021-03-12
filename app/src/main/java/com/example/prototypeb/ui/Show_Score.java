package com.example.prototypeb.ui;

import android.widget.TextView;

import com.example.prototypeb.controller.file_connections.File_connections;


public class Show_Score {
    public static TextView points_text;
    public static void setPoints_text(TextView points_text_in){
        points_text = points_text_in;
    }
    public void update_points(File_connections file_connections){
        if(points_text!=null){
            points_text.setText(file_connections.getScore() + "");
        }

    }
}

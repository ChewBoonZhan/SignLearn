package com.example.prototypeb.ui;

import android.widget.TextView;

import com.example.prototypeb.controller.file_connections.File_connections;


public class Show_Score {
    // the textview that shows score that users get
    public static TextView points_text;

    // sets the textview to show user's score
    public static void setPoints_text(TextView points_text_in){
        points_text = points_text_in;
    }

    /**
     * Updates the UI's score to the most recent score in the file
     * @param file_connections - file connection to access the user's score to be shown on the UI
     */
    public void update_points(File_connections file_connections){

        // if the textview to display the user's score is not NULL, that is its being set.
        if(points_text!=null){

            // update the score shown on the UI based on the score stored on file.
            points_text.setText(file_connections.getScore() + "");
        }

    }
}

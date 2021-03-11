package com.example.prototypeb.controller.lesson_unlocking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.choice_message.Two_choice_message;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen;
import com.example.prototypeb.controller.toast.Success_toast;
import com.example.prototypeb.ui.Show_Score;
import com.example.prototypeb.ui.game.Game_components.Game_components;
import com.example.prototypeb.ui.lesson.LessonFragment;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Lesson_topics;

import java.util.ArrayList;

public class Lesson_can_be_unlocked {
    private Context lesson_can_be_unlocked_context;
    private String lesson_title;
    private int score_required;
    private String lesson_to_be_unlocked;
    private Lesson_topics lesson_topics;
    private int score;
    private View v;
    public Lesson_can_be_unlocked(Context context,String lesson_title, int score_required,String lesson_to_be_unlocked,Lesson_topics lesson_topics,int score,View v){
        lesson_can_be_unlocked_context = context;
        this.lesson_title =lesson_title.toUpperCase();
        this.score_required = score_required;
        this.lesson_to_be_unlocked = lesson_to_be_unlocked;
        this.lesson_topics = lesson_topics;
        this.score = score;
        this.v = v;
    }
    public void show_choice_message(){
        String message_content = "Are you sure you want to unlock \"" + lesson_to_be_unlocked + "\" for " + score_required + "?";
        DialogInterface.OnClickListener positive_choice = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                File_connections file_connections = new File_connections(lesson_can_be_unlocked_context);
                file_connections.unlock_category(lesson_to_be_unlocked);

                //reduce the score
                score = score-score_required;
                file_connections.update_score(score);


                //set the button to be unlocked
                v.setOnClickListener(lesson_topics.get_unlocked_On_click());

                int color = ContextCompat.getColor(lesson_can_be_unlocked_context, R.color.primary);
                v.setBackgroundColor(color);

                //show notifi_textview
                Get_button_index get_button_index = new Get_button_index();
                ArrayList <TextView> category_notifi = LessonFragment.getCategory_notifi();
                category_notifi.get(get_button_index.get_index(lesson_to_be_unlocked)).setVisibility(View.VISIBLE);


                new Success_toast(lesson_can_be_unlocked_context,"Sucessfully unlocked: " + lesson_to_be_unlocked).show_toast();

            }
        };
        DialogInterface.OnClickListener negative_choice = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
                dialog.dismiss();
            }
        };

        Two_choice_message two_choice_message = new Two_choice_message(lesson_can_be_unlocked_context,lesson_title,message_content,"Yes","No",positive_choice,negative_choice);
        two_choice_message.show_message();
    }

}


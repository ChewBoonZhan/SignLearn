package com.example.prototypeb.controller.lesson_unlocking;

import android.content.Context;
import android.view.View;

import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.ui.game.Game_components.Game_components;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Lesson_topics;

import java.util.HashMap;

public class Lesson_unlocking {
    private Context lesson_unlock_context;
    private App_data app_data;
    private File_connections file_connections;
    private Lesson_topics lesson_topics;
    private View v;
    public Lesson_unlocking(Context context,Lesson_topics lesson_topics,View v){
        lesson_unlock_context = context;
        this.v = v;
        file_connections = new File_connections(lesson_unlock_context);
        app_data = new App_data();
        this.lesson_topics = lesson_topics;
    }
    public void user_clicks_locked_lesson(){
        int score = file_connections.getScore();
        int required_score = get_required_score(lesson_topics);
        if(score >= required_score ){
            //gives out a mesage box, saying enough points, and do you want to unlock.
            String lesson_to_be_unlocked =lesson_topics.toString();
            String title = "Unlock: " + lesson_to_be_unlocked;

            Lesson_can_be_unlocked lesson_can_be_unlocked = new Lesson_can_be_unlocked(lesson_unlock_context,title,required_score,lesson_to_be_unlocked, lesson_topics,score,v);
            lesson_can_be_unlocked.show_choice_message();
        }
        else if(score < required_score){
            Lesson_cannot_be_unlocked lesson_cannot_be_unlocked = new Lesson_cannot_be_unlocked(lesson_unlock_context,required_score);
            lesson_cannot_be_unlocked.show_toast();
            //send out a warning, saying not enough points
        }

    }
    private int get_required_score(Lesson_topics lesson_topics){

        return(lesson_topics.get_required_score());

    }
}

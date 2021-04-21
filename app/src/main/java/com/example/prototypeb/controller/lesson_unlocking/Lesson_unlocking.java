package com.example.prototypeb.controller.lesson_unlocking;

import android.content.Context;
import android.view.View;

import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.ui.game.Game_components.Game_components;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Lesson_topics;

import java.util.HashMap;

public class Lesson_unlocking {
    // context for unlocking lesson
    private Context lesson_unlock_context;

    // app data to get category of app
    private App_data app_data;

    // file connection to unlock lesson
    private File_connections file_connections;


    private Lesson_topics lesson_topics;

    // button clicked for lesson to be unlocked
    private View v;

    /**
     * Constructor for lesson unlocking
     * @param context - context for unlocking new lesson
     * @param lesson_topics - lesson topic to be unlocked
     * @param v -button clicked for lesson to be unlocked
     */
    public Lesson_unlocking(Context context,Lesson_topics lesson_topics,View v){
        lesson_unlock_context = context;
        this.v = v;
        file_connections = new File_connections(lesson_unlock_context);
        app_data = new App_data();
        this.lesson_topics = lesson_topics;
    }

    /**
     * onclick for locked lesson
     */
    public void user_clicks_locked_lesson(){

        // get current score in file connection
        int score = file_connections.getScore();

        // get required score to unlock the lesson
        int required_score = get_required_score(lesson_topics);

        // if user have sufficient score to unlock the lesson
        if(score >= required_score ){
            //gives out a mesage box, saying enough points, and do you want to unlock.
            String lesson_to_be_unlocked =lesson_topics.toString();
            String title = "Unlock: " + lesson_to_be_unlocked;

            // create new instance of lesson_can_be_unlocked to handle unlocked lesson
            Lesson_can_be_unlocked lesson_can_be_unlocked = new Lesson_can_be_unlocked(lesson_unlock_context,title,required_score,lesson_to_be_unlocked, lesson_topics,score,v);

            // show choice message if user wish to unlock the lesson
            lesson_can_be_unlocked.show_choice_message();
        }
        else if(score < required_score){

            // not sufficient score is acquired to unlock the lesson
            Lesson_cannot_be_unlocked lesson_cannot_be_unlocked = new Lesson_cannot_be_unlocked(lesson_unlock_context,required_score);

            // show toast of lesson cannot be unlocked due to lack of score
            lesson_cannot_be_unlocked.show_toast();
            //send out a warning, saying not enough points
        }

    }

    /**
     * get the required score to unlock the lesson category
     * @param lesson_topics - lesson topic to be get required score to unlock
     * @return required_score - integer to represent required score to unlock the category
     */
    private int get_required_score(Lesson_topics lesson_topics){

        return(lesson_topics.get_required_score());

    }
}

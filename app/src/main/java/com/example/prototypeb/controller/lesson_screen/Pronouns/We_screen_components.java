package com.example.prototypeb.controller.lesson_screen.Pronouns;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen_components;

public class We_screen_components implements Lesson_screen_components {
    public int get_gif_id(){
        return R.drawable.we;
    }
    public int get_title_id(){
        return R.string.we_title;
    }
    public int get_content_id(){
        return R.string.we_content;
    }

}
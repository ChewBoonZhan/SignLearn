package com.example.prototypeb.controller.lesson_screen.Adverbs;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen_components;

public class No_screen_components implements Lesson_screen_components {
    public int get_gif_id(){
        return R.drawable.no;
    }
    public int get_title_id(){
        return R.string.no_title;
    }
    public int get_content_id(){
        return R.string.no_content;
    }

}

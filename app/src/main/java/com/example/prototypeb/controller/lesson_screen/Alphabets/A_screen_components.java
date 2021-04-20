package com.example.prototypeb.controller.lesson_screen.Alphabets;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen_components;

public class A_screen_components implements Lesson_screen_components {
    /**
     * Getter for gif_id
     * @return gif_id - id for gif to be inserted as gif to be viewed by user
     */
    public int get_gif_id(){
        return R.drawable.alpha_a;
    }

    /**
     * Getter for title_id
     * @return title_id - id for title to be inserted into screen to let user know what syllabus user is un
     */
    public int get_title_id(){
        return R.string.a_title;
    }
    /**
     * getter for content_id
     * @return content_id - id for content to be inserted into screen, to teach user about the syllabus
     */
    public int get_content_id(){
        return R.string.a_content;
    }

}

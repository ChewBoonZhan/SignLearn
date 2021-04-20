package com.example.prototypeb.controller.lesson_screen;

import java.io.Serializable;

public interface Lesson_screen_components extends Serializable {
    /**
     * Getter for gif id on lesson screen
     * @return gif_id - id of the gif to set the gif
     */
    public int get_gif_id();

    /**
     * Getter for title id on lesson screen
     * @return title_id - id of the text for title on lesson screen
     */
    public int get_title_id();

    /**
     * getter for content id on lesson screen
     * @return content_id - id for the text for displayed on lesson screen
     */
    public int get_content_id();

}

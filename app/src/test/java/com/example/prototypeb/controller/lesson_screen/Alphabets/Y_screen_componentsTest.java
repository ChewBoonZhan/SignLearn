package com.example.prototypeb.controller.lesson_screen.Alphabets;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class Y_screen_componentsTest extends TestCase {
    private Y_screen_components y_screen_components = new Y_screen_components();
    public void testGet_gif_id() {
        assertEquals(y_screen_components.get_gif_id(), R.drawable.alpha_y);
    }

    public void testGet_title_id() {
        assertEquals(y_screen_components.get_title_id(),R.string.y_title);
    }

    public void testGet_content_id() {
        assertEquals(y_screen_components.get_content_id(),R.string.y_content);
    }
}
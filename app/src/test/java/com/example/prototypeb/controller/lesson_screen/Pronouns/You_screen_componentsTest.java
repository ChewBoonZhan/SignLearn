package com.example.prototypeb.controller.lesson_screen.Pronouns;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class You_screen_componentsTest extends TestCase {
    private You_screen_components you_screen_components = new You_screen_components();
    public void testGet_gif_id() {
        assertEquals(you_screen_components.get_gif_id(), R.drawable.you);
    }

    public void testGet_title_id() {
        assertEquals(you_screen_components.get_title_id(),R.string.you_title);
    }

    public void testGet_content_id() {
        assertEquals(you_screen_components.get_content_id(),R.string.you_content);
    }
}
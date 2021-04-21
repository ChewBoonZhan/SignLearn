package com.example.prototypeb.controller.lesson_screen.Adverbs;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class Almost_screen_componentsTest extends TestCase {
    private Almost_screen_components almost_screen_components = new Almost_screen_components();
    public void testGet_gif_id() {
        assertEquals(almost_screen_components.get_gif_id(), R.drawable.almost);
    }

    public void testGet_title_id() {
        assertEquals(almost_screen_components.get_title_id(),R.string.almost_title);
    }

    public void testGet_content_id() {
        assertEquals(almost_screen_components.get_content_id(),R.string.almost_content);
    }
}
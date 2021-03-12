package com.example.prototypeb.controller.lesson_screen.Adverbs;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class Yes_screen_componentsTest extends TestCase {

    private Yes_screen_components yes_screen_components = new Yes_screen_components();
    public void testGet_gif_id() {
        assertEquals(yes_screen_components.get_gif_id(), R.drawable.yes);
    }

    public void testGet_title_id() {
        assertEquals(yes_screen_components.get_title_id(),R.string.yes_title);
    }

    public void testGet_content_id() {
        assertEquals(yes_screen_components.get_content_id(),R.string.yes_content);
    }
}
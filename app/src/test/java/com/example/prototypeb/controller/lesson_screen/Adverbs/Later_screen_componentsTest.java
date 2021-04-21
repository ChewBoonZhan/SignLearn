package com.example.prototypeb.controller.lesson_screen.Adverbs;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class Later_screen_componentsTest extends TestCase {
    private Later_screen_components later_screen_components = new Later_screen_components();
    public void testGet_gif_id() {
        assertEquals(later_screen_components.get_gif_id(), R.drawable.later);
    }

    public void testGet_title_id() {
        assertEquals(later_screen_components.get_title_id(),R.string.later_title);
    }

    public void testGet_content_id() {
        assertEquals(later_screen_components.get_content_id(),R.string.later_content);
    }
}
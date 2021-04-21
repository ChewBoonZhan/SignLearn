package com.example.prototypeb.controller.lesson_screen.Pronouns;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class We_screen_componentsTest extends TestCase {
    private We_screen_components we_screen_components = new We_screen_components();
    public void testGet_gif_id() {
        assertEquals(we_screen_components.get_gif_id(), R.drawable.we);
    }

    public void testGet_title_id() {
        assertEquals(we_screen_components.get_title_id(),R.string.we_title);
    }

    public void testGet_content_id() {
        assertEquals(we_screen_components.get_content_id(),R.string.we_content);
    }
}
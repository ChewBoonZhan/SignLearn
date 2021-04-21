package com.example.prototypeb.controller.lesson_screen.Attachments;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class Adore_screen_componentsTest extends TestCase {

    private Adore_screen_components adore_screen_components = new Adore_screen_components();
    public void testGet_gif_id() {
        assertEquals(adore_screen_components.get_gif_id(), R.drawable.adore);
    }

    public void testGet_title_id() {
        assertEquals(adore_screen_components.get_title_id(),R.string.adore_title);
    }

    public void testGet_content_id() {
        assertEquals(adore_screen_components.get_content_id(),R.string.adore_content);
    }
}
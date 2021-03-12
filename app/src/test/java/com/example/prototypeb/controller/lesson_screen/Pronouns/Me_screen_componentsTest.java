package com.example.prototypeb.controller.lesson_screen.Pronouns;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class Me_screen_componentsTest extends TestCase {
    private Me_screen_components me_screen_components = new Me_screen_components();
    public void testGet_gif_id() {
        assertEquals(me_screen_components.get_gif_id(), R.drawable.i_me);
    }

    public void testGet_title_id() {
        assertEquals(me_screen_components.get_title_id(),R.string.me_title);
    }

    public void testGet_content_id() {
        assertEquals(me_screen_components.get_content_id(),R.string.me_content);
    }
}
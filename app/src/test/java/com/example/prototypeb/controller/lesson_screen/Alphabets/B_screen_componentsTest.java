package com.example.prototypeb.controller.lesson_screen.Alphabets;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class B_screen_componentsTest extends TestCase {

    private B_screen_components b_screen_components = new B_screen_components();
    public void testGet_gif_id() {
        assertEquals(b_screen_components.get_gif_id(), R.drawable.alpha_b);
    }

    public void testGet_title_id() {
        assertEquals(b_screen_components.get_title_id(),R.string.b_title);
    }

    public void testGet_content_id() {
        assertEquals(b_screen_components.get_content_id(),R.string.b_content);
    }
}
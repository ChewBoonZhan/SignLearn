package com.example.prototypeb.controller.lesson_screen.Alphabets;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class A_screen_componentsTest extends TestCase {

    private A_screen_components a_screen_components = new A_screen_components();
    public void testGet_gif_id() {
        assertEquals(a_screen_components.get_gif_id(), R.drawable.alpha_a);

    }

    public void testGet_title_id() {
        assertEquals(a_screen_components.get_title_id(),R.string.a_title);
    }

    public void testGet_content_id() {
        assertEquals(a_screen_components.get_content_id(),R.string.a_content);
    }
}
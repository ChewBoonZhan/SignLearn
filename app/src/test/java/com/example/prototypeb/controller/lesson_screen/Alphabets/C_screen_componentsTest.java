package com.example.prototypeb.controller.lesson_screen.Alphabets;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class C_screen_componentsTest extends TestCase {
    private C_screen_components c_screen_components = new C_screen_components();
    public void testGet_gif_id() {
        assertEquals(c_screen_components.get_gif_id(), R.drawable.alpha_c);
    }

    public void testGet_title_id() {
        assertEquals(c_screen_components.get_title_id(),R.string.c_title);
    }

    public void testGet_content_id() {
        assertEquals(c_screen_components.get_content_id(),R.string.c_content);
    }
}
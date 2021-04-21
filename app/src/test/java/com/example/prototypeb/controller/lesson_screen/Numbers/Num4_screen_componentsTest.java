package com.example.prototypeb.controller.lesson_screen.Numbers;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class Num4_screen_componentsTest extends TestCase {
    Num4_screen_components num4_screen_components = new Num4_screen_components();
    public void testGet_gif_id() {
        assertEquals(num4_screen_components.get_gif_id(), R.drawable.four);
    }

    public void testGet_title_id() {
        assertEquals(num4_screen_components.get_title_id(),R.string.num4_title);
    }

    public void testGet_content_id() {
        assertEquals(num4_screen_components.get_content_id(),R.string.num4_content);
    }
}
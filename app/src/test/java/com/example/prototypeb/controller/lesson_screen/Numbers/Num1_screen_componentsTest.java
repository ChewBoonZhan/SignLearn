package com.example.prototypeb.controller.lesson_screen.Numbers;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class Num1_screen_componentsTest extends TestCase {
    private Num1_screen_components num1_screen_components = new Num1_screen_components();
    public void testGet_gif_id() {
        assertEquals(num1_screen_components.get_gif_id(), R.drawable.one);
    }

    public void testGet_title_id() {
        assertEquals(num1_screen_components.get_title_id(),R.string.num1_title);
    }

    public void testGet_content_id() {
        assertEquals(num1_screen_components.get_content_id(),R.string.num1_content);
    }
}
package com.example.prototypeb.controller.lesson_screen.Numbers;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class Num3_screen_componentsTest extends TestCase {
    private Num3_screen_components num3_screen_components =new Num3_screen_components();
    public void testGet_gif_id() {
        assertEquals(num3_screen_components.get_gif_id(), R.drawable.three);
    }

    public void testGet_title_id() {
        assertEquals(num3_screen_components.get_title_id(),R.string.num3_title);
    }

    public void testGet_content_id() {
        assertEquals(num3_screen_components.get_content_id(),R.string.num3_content);
    }
}
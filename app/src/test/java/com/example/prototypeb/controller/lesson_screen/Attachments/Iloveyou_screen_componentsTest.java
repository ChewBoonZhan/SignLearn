package com.example.prototypeb.controller.lesson_screen.Attachments;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class Iloveyou_screen_componentsTest extends TestCase {
    private Iloveyou_screen_components iloveyou_screen_components = new Iloveyou_screen_components();
    public void testGet_gif_id() {
        assertEquals(iloveyou_screen_components.get_gif_id(), R.drawable.i_love_u);
    }

    public void testGet_title_id() {
        assertEquals(iloveyou_screen_components.get_title_id(),R.string.iloveyou_title);
    }

    public void testGet_content_id() {
        assertEquals(iloveyou_screen_components.get_content_id(),R.string.iloveyou_content);
    }
}
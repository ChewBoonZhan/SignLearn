package com.example.prototypeb.controller.lesson_screen.Attachments;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class Dislike_screen_componentsTest extends TestCase {
    private Dislike_screen_components dislike_screen_components = new Dislike_screen_components();
    public void testGet_gif_id() {
        assertEquals(dislike_screen_components.get_gif_id(), R.drawable.dislike);
    }

    public void testGet_title_id() {
        assertEquals(dislike_screen_components.get_title_id(),R.string.dislike_title);
    }

    public void testGet_content_id() {
        assertEquals(dislike_screen_components.get_content_id(),R.string.dislike_content);
    }
}
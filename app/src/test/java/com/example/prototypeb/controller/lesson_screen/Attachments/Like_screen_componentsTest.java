package com.example.prototypeb.controller.lesson_screen.Attachments;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class Like_screen_componentsTest extends TestCase {
    private Like_screen_components like_screen_components = new Like_screen_components();
    public void testGet_gif_id() {
        assertEquals(like_screen_components.get_gif_id(), R.drawable.like);
    }

    public void testGet_title_id() {
        assertEquals(like_screen_components.get_title_id(),R.string.like_title);
    }

    public void testGet_content_id() {
        assertEquals(like_screen_components.get_content_id(),R.string.like_content);
    }
}
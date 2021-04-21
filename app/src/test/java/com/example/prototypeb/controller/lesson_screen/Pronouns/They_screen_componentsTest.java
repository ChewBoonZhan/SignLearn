package com.example.prototypeb.controller.lesson_screen.Pronouns;

import com.example.prototypeb.R;

import junit.framework.TestCase;

public class They_screen_componentsTest extends TestCase {
    private They_screen_components they_screen_components = new They_screen_components();
    public void testGet_gif_id() {
        assertEquals(they_screen_components.get_gif_id(), R.drawable.they);
    }

    public void testGet_title_id() {
        assertEquals(they_screen_components.get_title_id(),R.string.they_title);
    }

    public void testGet_content_id() {
        assertEquals(they_screen_components.get_content_id(),R.string.they_content);
    }
}
package com.example.prototypeb.controller.lesson_screen.Adverbs;

import com.example.prototypeb.R;

import junit.framework.TestCase;

import org.junit.Before;

public class No_screen_componentsTest extends TestCase {

    private No_screen_components no_screen_components = new No_screen_components();
    public void testGet_gif_id() {
        assertEquals(no_screen_components.get_gif_id(), R.drawable.no);
    }

    public void testGet_title_id() {
        assertEquals(no_screen_components.get_title_id(),R.string.no_title);
    }

    public void testGet_content_id() {
        assertEquals(no_screen_components.get_content_id(),R.string.no_content);
    }
}
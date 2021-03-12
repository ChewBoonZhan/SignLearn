package com.example.prototypeb.controller.app_data;

import junit.framework.TestCase;

public class Intent_keyTest extends TestCase {
    private Intent_key intent_key = new Intent_key();
    public void testGetScreen_component() {
        assertEquals(intent_key.getScreen_component(),"screen_components");
    }

    public void testGetTranslator_label() {
        assertEquals(intent_key.getTranslator_label(),"correct_translator_label");
    }

    public void testGetTranslator_lesson_topics() {
        assertEquals(intent_key.getTranslator_lesson_topics(),"translator_lesson_topics");
    }

    public void testGetTranslator_screen_title_id() {
        assertEquals(intent_key.getTranslator_screen_title_id(),"translator_screen_title_id");
    }
}
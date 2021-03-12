package com.example.prototypeb.controller.app_data;

import junit.framework.TestCase;

public class App_dataTest extends TestCase {

    public void testGetCategories() {
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();
        String[] actual_categories = new String[]{"Adverbs","Alphabets", "Attachments","Numbers","Pronoun"};
        int length = categories.length;
        int actual_length = actual_categories.length;
        assertEquals(length,actual_length);
        for(int i = 0;i<length;i++){
            assertEquals(categories[i],actual_categories[i]);
        }

    }
}
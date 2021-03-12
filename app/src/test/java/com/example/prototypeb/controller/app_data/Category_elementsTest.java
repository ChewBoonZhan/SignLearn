package com.example.prototypeb.controller.app_data;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;

public class Category_elementsTest extends TestCase {
    private HashMap<String, ArrayList<String>> actual_category_elements;
    private App_data app_data = new App_data();
    public void testGetCategory_elements() {
        init_actual_category_elements();
        String[] categories = app_data.getCategories();
        Category_elements category_elements_list = new Category_elements();
        HashMap<String, ArrayList<String>> category_elements = category_elements_list.getCategory_elements();
        int length = categories.length;
        for(int i = 0;i<length;i++){
            ArrayList <String> specific_category_elements =category_elements.get(categories[i]);
            ArrayList <String>actual_specific_category_elements =actual_category_elements.get(categories[i]);
            int specific_category_elements_length = specific_category_elements.size();
            int actual_specific_category_elements_length = actual_specific_category_elements.size();
            assertEquals(specific_category_elements_length,actual_specific_category_elements_length);
            for(int j = 0;j<specific_category_elements_length;j++){
                assertEquals(specific_category_elements.get(j),actual_specific_category_elements.get(j));
            }
        }


    }
    private void init_actual_category_elements(){
        actual_category_elements = new  HashMap <String, ArrayList <String>>();
        String[] categories = app_data.getCategories();
        ArrayList <String> adverb_elements = new ArrayList<String>();
        adverb_elements.add("Yes");
        adverb_elements.add("No");


        actual_category_elements.put(categories[0],adverb_elements);

        ArrayList <String> alphabets_elements = new ArrayList<String>();
        alphabets_elements.add("\"A\"");
        alphabets_elements.add("\"B\"");
        alphabets_elements.add("\"C\"");
        alphabets_elements.add("\"Y\"");

        actual_category_elements.put(categories[1],alphabets_elements);


        ArrayList <String> attachments_elements = new ArrayList<String>();
        attachments_elements.add("Like");
        attachments_elements.add("Dislike");
        attachments_elements.add("I Love You");

        actual_category_elements.put(categories[2],attachments_elements);

        ArrayList <String> numbers_elements = new ArrayList<String>();
        numbers_elements.add("One");
        numbers_elements.add("Two");
        numbers_elements.add("Ten");

        actual_category_elements.put(categories[3],numbers_elements);

        ArrayList <String> pronouns_elements = new ArrayList<String>();
        pronouns_elements.add("Me");
        pronouns_elements.add("You");


        actual_category_elements.put(categories[4],pronouns_elements);

    }
}
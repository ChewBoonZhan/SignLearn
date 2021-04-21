package com.example.prototypeb.controller.app_data;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;

public class Category_elementsTest extends TestCase {
    private HashMap<String, ArrayList<String>> actual_category_elements;
    private HashMap<Integer, ArrayList<String>> actual_testable_elements;
    private ArrayList<String> actual_not_testable_elements;
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
        adverb_elements.add("Almost");
        adverb_elements.add("Later");

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
        attachments_elements.add("Adore");

        actual_category_elements.put(categories[2],attachments_elements);

        ArrayList <String> numbers_elements = new ArrayList<String>();
        numbers_elements.add("One");
        numbers_elements.add("Two");
        numbers_elements.add("Three");
        numbers_elements.add("Four");
        numbers_elements.add("Ten");

        actual_category_elements.put(categories[3],numbers_elements);

        ArrayList <String> pronouns_elements = new ArrayList<String>();
        pronouns_elements.add("Me");
        pronouns_elements.add("You");
        pronouns_elements.add("They");
        pronouns_elements.add("We");

        actual_category_elements.put(categories[4],pronouns_elements);

    }
    public void testGet_category_elements_testable() {
        init_testable_element();
        Category_elements category_elements_list = new Category_elements();
        String[] categories = app_data.getCategories();
        int length = categories.length;
        for(int i= 0;i<length;i++){
            ArrayList <String> category_testable_elements = category_elements_list.get_category_elements_testable(i);
            ArrayList <String> category_actual_testable = actual_testable_elements.get(i);
            int length_received = category_testable_elements.size();

            int actual_length = category_actual_testable.size();

            assertEquals(length_received,actual_length);

            for(int j = 0;j<actual_length;j++){
                assertEquals(category_testable_elements.get(j),category_actual_testable.get(j));
            }



        }
    }
    private void init_testable_element(){
        actual_testable_elements = new  HashMap <Integer, ArrayList <String>>();
        ArrayList <String> adverb_elements = new ArrayList<String>();
        adverb_elements.add("Yes");
        adverb_elements.add("No");


        actual_testable_elements.put(0,adverb_elements);

        ArrayList <String> alphabets_elements = new ArrayList<String>();
        alphabets_elements.add("\"A\"");
        alphabets_elements.add("\"B\"");
        alphabets_elements.add("\"C\"");
        alphabets_elements.add("\"Y\"");

        actual_testable_elements.put(1,alphabets_elements);


        ArrayList <String> attachments_elements = new ArrayList<String>();
        attachments_elements.add("Like");
        attachments_elements.add("Dislike");
        attachments_elements.add("I Love You");


        actual_testable_elements.put(2,attachments_elements);

        ArrayList <String> numbers_elements = new ArrayList<String>();
        numbers_elements.add("One");
        numbers_elements.add("Two");


        actual_testable_elements.put(3,numbers_elements);

        ArrayList <String> pronouns_elements = new ArrayList<String>();
        pronouns_elements.add("Me");
        pronouns_elements.add("You");


        actual_testable_elements.put(4,pronouns_elements);
    }
    public void testCheck_element_not_testable() {
        Category_elements category_elements_list = new Category_elements();
        init_not_testable_elements();
        int length = actual_not_testable_elements.size();
        for (int i = 0;i<length;i++){
            assertEquals(category_elements_list.check_element_not_testable(actual_not_testable_elements.get(i)),true);
        }
    }
    private void init_not_testable_elements(){
        init_actual_category_elements();
        actual_not_testable_elements = new ArrayList<String>();
        String[] categories = app_data.getCategories();
        actual_not_testable_elements.add(actual_category_elements.get(categories[0]).get(2));
        actual_not_testable_elements.add(actual_category_elements.get(categories[0]).get(3));

        actual_not_testable_elements.add(actual_category_elements.get(categories[2]).get(3));

        actual_not_testable_elements.add(actual_category_elements.get(categories[3]).get(3));
        actual_not_testable_elements.add(actual_category_elements.get(categories[3]).get(2));
        actual_not_testable_elements.add(actual_category_elements.get(categories[3]).get(4));


        actual_not_testable_elements.add(actual_category_elements.get(categories[4]).get(3));
        actual_not_testable_elements.add(actual_category_elements.get(categories[4]).get(2));
    }

}
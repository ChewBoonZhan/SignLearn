package com.example.prototypeb.controller.app_data;

import android.util.Log;

import com.example.prototypeb.controller.category.Category_init;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Category_elements {
    /**
     * Constructor for category elements
     */
    public Category_elements(){
        init_category_elements();
        init_not_testable_elements();

    }
    // hashmap that maps category of sign language to the syllabus of sign language
    private HashMap<String, ArrayList<String>> category_elements = new  HashMap <String, ArrayList <String>>();

    // arraylist that stores elements that are not to be tested by users in translator
    private ArrayList <String> not_testable_elements = new ArrayList<String>();

    //Initializes elements that are not testable in translator
    private void init_not_testable_elements(){
        App_data app_data = new App_data();

        //elements that are not testable in translator are added into arraylist
        not_testable_elements.add(category_elements.get(app_data.getCategories()[0]).get(2));
        not_testable_elements.add(category_elements.get(app_data.getCategories()[0]).get(3));

        not_testable_elements.add(category_elements.get(app_data.getCategories()[2]).get(3));

        not_testable_elements.add(category_elements.get(app_data.getCategories()[3]).get(2));
        not_testable_elements.add(category_elements.get(app_data.getCategories()[3]).get(3));
        not_testable_elements.add(category_elements.get(app_data.getCategories()[3]).get(4));

        not_testable_elements.add(category_elements.get(app_data.getCategories()[4]).get(2));
        not_testable_elements.add(category_elements.get(app_data.getCategories()[4]).get(3));
    }
    // initializes category elements, which are syllabus of categories
    private void init_category_elements(){
        App_data app_data = new App_data();
        // get array of categories for sign language
        String[] categories = app_data.getCategories();

        //arraylist are added into hashmap to be considered syllabus of categories of sign language
        ArrayList <String> adverb_elements = new ArrayList<String>();
        adverb_elements.add("Yes");
        adverb_elements.add("No");
        adverb_elements.add("Almost");
        adverb_elements.add("Later");


        category_elements.put(categories[0],adverb_elements);

        ArrayList <String> alphabets_elements = new ArrayList<String>();
        alphabets_elements.add("\"A\"");
        alphabets_elements.add("\"B\"");
        alphabets_elements.add("\"C\"");
        alphabets_elements.add("\"Y\"");

        category_elements.put(categories[1],alphabets_elements);


        ArrayList <String> attachments_elements = new ArrayList<String>();
        attachments_elements.add("Like");
        attachments_elements.add("Dislike");
        attachments_elements.add("I Love You");
        attachments_elements.add("Adore");

        category_elements.put(categories[2],attachments_elements);

        ArrayList <String> numbers_elements = new ArrayList<String>();
        numbers_elements.add("One");
        numbers_elements.add("Two");
        numbers_elements.add("Three");
        numbers_elements.add("Four");
        numbers_elements.add("Ten");

        category_elements.put(categories[3],numbers_elements);

        ArrayList <String> pronouns_elements = new ArrayList<String>();
        pronouns_elements.add("Me");
        pronouns_elements.add("You");
        pronouns_elements.add("They");
        pronouns_elements.add("We");



        category_elements.put(categories[4],pronouns_elements);


    }

    /**
     * This checks for syllabus that are testable using translator, based on category index which tells what category of sign language's syllabus is requested
     * @param category_index - index of category of sign language to get testable syllabus
     * @return testable_category_elements - arraylist of elements which can be tested using translator
     */
    public ArrayList <String> get_category_elements_testable(int category_index){
        ArrayList <String> category_elements_testable = new ArrayList<String>();
        App_data app_data = new App_data();

        //get array of categories
        String [] categories = app_data.getCategories();

        //get the category from array of categories based on input.
        String category = categories[category_index];

        //get arraylist of syllabus for category
        ArrayList <String> category_elements_total = category_elements.get(category);

        //getting the number of syllabus
        int length = category_elements_total.size();

        //add syllabus that are testable into an array to be returned
        for (int i = 0;i<length;i++){
            if(!check_element_not_testable(category_elements_total.get(i))){
                category_elements_testable.add(category_elements_total.get(i));
            }
        }
        return category_elements_testable;
    }

    /**
     * Getter for category elements, which is the hashmap of categories to syllabus.
     * @return category_elements - Hashmap that links categories to its arraylist of syllabus
     */
    public HashMap <String, ArrayList <String>> getCategory_elements(){
        return category_elements;
    }

    /**
     * This function checks if the elements are testable by translator or not.
     * @param category_element - category syllabus to check if the syllabus is testable by translator or not.
     * @return element_testable - boolean that determines if the element is testable by translator.
     */
    public boolean check_element_not_testable(String category_element){
        return (not_testable_elements.contains(category_element));

    }

    /**
     * Some syllabus has space, but due to processing has no more space.
     * In this case, we need to get back its original word with space.
     * @param string_with_no_space - string that has been processed and has no space
     * @return string_with_space - string that has been processed and added space.
     * if it's original word has no space, then the input word will be returned (base case)
     */
    public String string_with_space(String string_with_no_space){
        if(string_with_no_space.equals("iloveyou")){
            return "I Love You";
        }
        else{
            return string_with_no_space;
        }
    }


}

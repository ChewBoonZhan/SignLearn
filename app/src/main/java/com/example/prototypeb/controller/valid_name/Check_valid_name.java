package com.example.prototypeb.controller.valid_name;

import java.util.regex.Pattern;

public class Check_valid_name {
    // pattern of name, which is from a to z, and can have space
    private final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z ]{2,10}$");

    // error message of the name to be displayed to user
    private String error_message = "";

    /**
     * Constructor for checking valid name
     * @param name - name to be checked if it is valid
     * @param min_length - minimum length of name to be valid
     * @param max_length - maximum length of name to be valid
     * @return valid_name - true if the name is valid, false if it contains error
     */
    public boolean check_valid_name(String name, int min_length,int max_length){

        // remove space from the name
        String name_field_value_trimmed = name.trim();

        // if the name is empty or the name is just spaces
        if(name.isEmpty() || name_field_value_trimmed.isEmpty()){

            // return false, the name cannot be empty
            error_message = " cannot be empty.";
            return false;
        }
        // if name does not satisfy min length
        else if(name.length() < min_length || name_field_value_trimmed.length() < min_length){

            // return false, name must be longer than min length
            error_message=" must be longer than "+min_length + " characters.";
            return false;
        }
        // if name does not satisfy max length
        else if(name.length() > max_length || name_field_value_trimmed.length() > max_length){

            // return false, name must be shorter than max length
            error_message = " must be shorter than "+max_length + " characters.";
            return false;
        }
        //if name does not satisfy pattern of alphabet and space
        else if(!NAME_PATTERN.matcher(name).matches()){
            // return false, name must contain characters and spaces
            error_message = " must only contain Characters and Spaces.";
            return false;
        }
        else{
            error_message = "";
            // return true, name has no error
            return true;
        }
    }

    /**
     * Getter for error_message
     * @return error_message - error message of the name, if the name has no error then it returns ""
     */
    public String getError_message(){
        return error_message;
    }

}

package com.example.prototypeb.controller.valid_name;

import java.util.regex.Pattern;

public class Check_valid_name {
    private final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z ]{2,10}$");
    private String error_message = "";
    public boolean check_valid_name(String name, int min_length,int max_length){
        String name_field_value_trimmed = name.trim();
        if(name.isEmpty() || name_field_value_trimmed.isEmpty()){
            error_message = " cannot be empty.";
            return false;
        }
        else if(name.length() < min_length || name_field_value_trimmed.length() < min_length){
            error_message=" must be longer than "+min_length + " characters.";
            return false;
        }
        else if(name.length() > max_length || name_field_value_trimmed.length() > max_length){
            error_message = " must be shorter than "+max_length + " characters.";
            return false;
        }
        else if(!NAME_PATTERN.matcher(name).matches()){
            error_message = " must only contain Characters and Spaces.";
            return false;
        }
        else{
            error_message = "";
            return true;
        }
    }
    public String getError_message(){
        return error_message;
    }

}

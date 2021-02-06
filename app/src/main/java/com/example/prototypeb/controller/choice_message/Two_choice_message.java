package com.example.prototypeb.controller.choice_message;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class Two_choice_message {
    private Context message_context;
    private String message_title;
    private String message_contents;
    private String positive_choice_string;
    private String negative_choice_string;
    private DialogInterface.OnClickListener positive_choice;
    private DialogInterface.OnClickListener negative_choice;

    public Two_choice_message(Context context,String message_title, String message_contents, String positive_choice_string, String negative_choice_string, DialogInterface.OnClickListener positive_choice, DialogInterface.OnClickListener negative_choice){
        message_context = context;
        this.message_title = message_title;
        this.message_contents = message_contents;
        this.positive_choice_string = positive_choice_string;
        this.negative_choice_string = negative_choice_string;
        this.positive_choice = positive_choice;
        this.negative_choice = negative_choice;
    }
    public void show_message(){
        new AlertDialog.Builder(message_context).
                setTitle(message_title)
                .setMessage(message_contents)
                .setPositiveButton(positive_choice_string, positive_choice)
                .setNegativeButton(negative_choice_string, negative_choice)
                .create().show();
    }
}

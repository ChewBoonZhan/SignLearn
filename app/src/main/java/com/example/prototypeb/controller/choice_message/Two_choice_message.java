package com.example.prototypeb.controller.choice_message;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class Two_choice_message {
    //context to show the message in
    private Context message_context;

    //title of the message to be displayed to user
    private String message_title;

    //string to show content of the message to user
    private String message_contents;

    //positive choice button string
    private String positive_choice_string;

    //negative choice button string
    private String negative_choice_string;

    // on click listener and event for the positive choice
    private DialogInterface.OnClickListener positive_choice;
    // on click listener and event for the negative choice
    private DialogInterface.OnClickListener negative_choice;

    //alert dislog to be showned to user on the screen
    private AlertDialog.Builder alert_dialog;

    /**
     * Constructor for two choice message
     * @param context - Context to display the two_choice_message in
     * @param message_title - Title for the positive choice of the message to be showned to user
     * @param message_contents - string to show the message to user
     * @param positive_choice_string - positive choice button string for user to click on
     * @param negative_choice_string - negative choice button string for user to click on
     * @param positive_choice - positive choice on click listener and even trigger
     * @param negative_choice - negative choice on click listener and even trigger
     */
    public Two_choice_message(Context context,String message_title, String message_contents, String positive_choice_string, String negative_choice_string, DialogInterface.OnClickListener positive_choice, DialogInterface.OnClickListener negative_choice){
        //context for message to be displayed to user
        message_context = context;

        //title of the message to be showed to user
        this.message_title = message_title;

        //content of the message to be showed to user
        this.message_contents = message_contents;

        //string of the positive choice of the message
        this.positive_choice_string = positive_choice_string;

        //string of the negative choice of the message
        this.negative_choice_string = negative_choice_string;

        //positive choice on click and event
        this.positive_choice = positive_choice;

        //negative choice on click and event
        this.negative_choice = negative_choice;
    }

    /**
     * Show message to user
     */
    public void show_message() {

        //create alert dialog class
        alert_dialog = new AlertDialog.Builder(message_context);

        //set title of the alert dialog
        alert_dialog.setTitle(message_title);

        //set message of alert dialog
        alert_dialog.setMessage(message_contents);

        //set positive button's string and on click
        alert_dialog.setPositiveButton(positive_choice_string, positive_choice);

        //set negative button's string and on click
        alert_dialog.setNegativeButton(negative_choice_string, negative_choice);

        //create alert dialog
        alert_dialog.create();
        //show alert dialog to user
        alert_dialog.show();
    }

}

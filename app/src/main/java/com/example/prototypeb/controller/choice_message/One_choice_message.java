package com.example.prototypeb.controller.choice_message;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatDialogFragment;

public class One_choice_message extends AppCompatDialogFragment {
    //context to show the choice message to user
    private Context one_choice_message_context;

    //title of the message to be displayed to user
    private String message_title;

    //context to show message to user
    private String message_contents;

    //positive choice of the choice message to be displayed to user
    private String positive_choice_string;

    //onclick listener and action for the positive choice
    private DialogInterface.OnClickListener positive_choice;

    //alert dialog for the choice message
    private AlertDialog.Builder alert_dialog;

    /**
     * Constructor for the choice message
     * @param context - context to display the choice message in
     * @param message_title - Title of the choice message
     * @param message_contents - String to show to user in choice message
     * @param positive_choice_string - String to be shown on the positive choice button
     * @param positive_choice - On Click event and listener for the positive choice button
     */
    public One_choice_message(Context context,String message_title, String message_contents, String positive_choice_string, DialogInterface.OnClickListener positive_choice){
        //context for the one choice message
        one_choice_message_context = context;

        //set title for the message
        this.message_title = message_title;

        //set the string for the message content
        this.message_contents = message_contents;

        //set the string for the positive choice button string
        this.positive_choice_string = positive_choice_string;

        //set the on click for the positive choice
        this.positive_choice = positive_choice;

    }

    /**
     * show the one choice message for user to interact with
     */
    public void show_message() {

        //create a new alertdialog to display message to user
        alert_dialog = new AlertDialog.Builder(one_choice_message_context);

        //set the title of the message
        alert_dialog.setTitle(message_title);

        //set the string of the message
        alert_dialog.setMessage(message_contents);

        //set the positive choice button's string and on click
        alert_dialog.setPositiveButton(positive_choice_string, positive_choice);

        //create the dialog
        alert_dialog.create();

        //show the dialog to user
        alert_dialog.show();
    }
}

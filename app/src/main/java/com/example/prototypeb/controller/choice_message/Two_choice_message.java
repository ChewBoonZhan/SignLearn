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
    private AlertDialog.Builder alert_dialog;
    public Two_choice_message(Context context,String message_title, String message_contents, String positive_choice_string, String negative_choice_string, DialogInterface.OnClickListener positive_choice, DialogInterface.OnClickListener negative_choice){
        message_context = context;
        this.message_title = message_title;
        this.message_contents = message_contents;
        this.positive_choice_string = positive_choice_string;
        this.negative_choice_string = negative_choice_string;
        this.positive_choice = positive_choice;
        this.negative_choice = negative_choice;
    }
    public void show_message() {
        alert_dialog = new AlertDialog.Builder(message_context);
        alert_dialog.setTitle(message_title);
        alert_dialog.setMessage(message_contents);
        alert_dialog.setPositiveButton(positive_choice_string, positive_choice);
        alert_dialog.setNegativeButton(negative_choice_string, negative_choice);
        alert_dialog.create();
        alert_dialog.show();
    }
    public AlertDialog.Builder getAlert_dialog(){
        return this.alert_dialog;
    }
}

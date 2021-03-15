package com.example.prototypeb.controller.choice_message;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatDialogFragment;

public class One_choice_message extends AppCompatDialogFragment {
    private Context one_choice_message_context;
    private String message_title;
    private String message_contents;
    private String positive_choice_string;
    private DialogInterface.OnClickListener positive_choice;
    private AlertDialog.Builder alert_dialog;
    public One_choice_message(Context context,String message_title, String message_contents, String positive_choice_string, DialogInterface.OnClickListener positive_choice){
        one_choice_message_context = context;
        this.message_title = message_title;
        this.message_contents = message_contents;
        this.positive_choice_string = positive_choice_string;

        this.positive_choice = positive_choice;

    }

    public void show_message() {
        alert_dialog = new AlertDialog.Builder(one_choice_message_context);
        alert_dialog.setTitle(message_title);
        alert_dialog.setMessage(message_contents);
        alert_dialog.setPositiveButton(positive_choice_string, positive_choice);
        alert_dialog.create();
        alert_dialog.show();
    }
}

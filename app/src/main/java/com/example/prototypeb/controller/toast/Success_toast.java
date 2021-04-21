package com.example.prototypeb.controller.toast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.prototypeb.R;

public class Success_toast extends Custom_toasts{

    //string of toast to be shown to user
    private String message;

    //colour of toast is the primary color of the app
    private final int TOAST_COLOR = R.color.primary;

    /**
     * constuctor for success toast
     * @param context - context to display the toast in
     * @param message - string of message to be dusplayed to user
     */
    public Success_toast(Context context, String message){
        super(context);
        this.message = message;
    }

    /**
     * Constructor for success_toast
     * @param context - context to display the toast in
     */
    public Success_toast(Context context){
        super(context);
    }

    /**
     * show the toast to user, based on messsage given to the class via constructor
     */
    public void show_toast(){
        super.show_toast(this.message,TOAST_COLOR);
    }

    /**
     * Show toast to user based on input message, and the duration of the toast depends on if the
     * toast is to be showned to user for long time or not.
     * @param message - String to show toast to user
     * @param long_toast - Boolean if the toast to be displayed to user is long or short
     */
    public void show_toast(String message,boolean long_toast){
        super.show_toast(message,TOAST_COLOR,long_toast);
    }
}

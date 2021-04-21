package com.example.prototypeb.controller.toast;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.prototypeb.R;

public class Alert_toast extends Custom_toasts{
    // String to be shown on the toast
    private String message;

    //alert toast colour
    private final int TOAST_COLOR = R.color.warning;

    /**
     * constructor for alert toast
     * @param context - context to display toast in
     * @param message - string of message to be displayed on the toast
     */
    public Alert_toast(Context context, String message){
        super(context);
        this.message = message;
    }

    /**
     * constructor for alert toast
     * @param context - context to display toast in
     */
    public Alert_toast(Context context){
        super(context);
    }

    /**
     * Show toast to user, with the message given in constructor
     */
    public void show_toast(){
        super.show_toast(this.message,TOAST_COLOR);
    }

    /**
     * Show toast to user, with the message given into the function
     * @param message - String to display toast to user
     * @param long_toast - if the toast needs to be displayed for long time
     */
    public void show_toast(String message,boolean long_toast){
        super.show_toast(message,TOAST_COLOR,long_toast);
    }

}

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


    private String message;
    private final int TOAST_COLOR = R.color.primary;
    public Success_toast(Context context, String message){
        super(context);
        this.message = message;
    }

    public void show_toast(){
        super.show_toast(this.message,TOAST_COLOR);
    }
}

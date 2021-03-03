package com.example.prototypeb.controller.toast;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.prototypeb.R;

public class Alert_toast implements Custom_toasts{
    private Context alert_toast_context;
    private String message;


    public Alert_toast(Context context, String message){
        alert_toast_context = context;
        this.message = message;
    }
    public void show_toast(){
        Toast toast = Toast.makeText(alert_toast_context, message, (int)duration);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        int background_color = ContextCompat.getColor(alert_toast_context, R.color.warning);
        view.getBackground().setColorFilter((background_color), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);

        int string_color = ContextCompat.getColor(alert_toast_context, R.color.white);
        text.setTextColor((string_color));
        text.setGravity(Gravity.CENTER);

        toast.show();
    }

}

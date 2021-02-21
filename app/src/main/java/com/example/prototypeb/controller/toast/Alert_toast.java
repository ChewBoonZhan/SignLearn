package com.example.prototypeb.controller.toast;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Alert_toast implements Custom_toasts{
    private Context alert_toast_context;
    private String message;
    public String background_color = "#ffc3bf";
    public Alert_toast(Context context, String message){
        alert_toast_context = context;
        this.message = message;
    }
    public void show_toast(){
        Toast toast = Toast.makeText(alert_toast_context, message, (int)duration);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(Color.parseColor(background_color), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.parseColor(string_color));
        text.setGravity(Gravity.CENTER);

        toast.show();
    }

}

package com.example.prototypeb.controller.toast;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.prototypeb.R;

public class Custom_toasts {
    private Context context;
    private final int TOAST_TEXT_COLOR = R.color.white;
    private final int TOAST_TEXT_GRAVITY = Gravity.CENTER;
    private boolean toast_shown = false;
    private Toast toast;

    public Custom_toasts(Context context){
        this.context = context;

    }
    public Toast getToast(){
        return toast;
    }
    public void show_toast(String message,int toast_color){
        toast_shown = false;

        toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        int background_color = ContextCompat.getColor(context, toast_color);
        view.getBackground().setColorFilter((background_color), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);

        int string_color = ContextCompat.getColor(context, TOAST_TEXT_COLOR);
        text.setTextColor((string_color));
        text.setGravity(TOAST_TEXT_GRAVITY);

        toast.show();
        toast_shown = true;
    }
    public boolean getToast_shown(){
        return toast_shown;
    }

}

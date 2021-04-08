package com.example.prototypeb.controller.toast;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.prototypeb.MainActivity;
import com.example.prototypeb.R;

public abstract class Custom_toasts {
    private Context context;
    private final int TOAST_TEXT_COLOR = R.color.white;
    private final int TOAST_TEXT_GRAVITY = Gravity.CENTER;
    private final int DELAY_BETWEEN_TOAST = 2000;  // this is in milliseconds
    private boolean toast_shown = false;
    private Toast toast;

    public Custom_toasts(Context context){
        this.context = context;

    }
    public Toast getToast(){
        return toast;
    }
    public void show_toast(String message,int toast_color){
        show_toast(message,toast_color,false);
    }
    public void show_toast(String message,int toast_color,boolean long_toast){
        if(!MainActivity.toast_shown){
            toast_shown = false;
            int toast_duration;
            if(long_toast){
                toast_duration = Toast.LENGTH_LONG;
            }
            else{
                toast_duration = Toast.LENGTH_SHORT;
            }
            toast = Toast.makeText(context, message, toast_duration);
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
            MainActivity.toast_shown = true;

            // for one toast to be displayed at a time only.
            CountDownTimer countDownTimer = new CountDownTimer(DELAY_BETWEEN_TOAST, 1000) {

                @Override
                public void onTick(long l) {

                }

                public void onFinish() {
                    MainActivity.toast_shown = false;
                }
            };
            countDownTimer.start();
        }

    }
    public boolean getToast_shown(){
        return toast_shown;
    }

}

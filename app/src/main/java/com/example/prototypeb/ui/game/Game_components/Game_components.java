package com.example.prototypeb.ui.game.Game_components;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototypeb.controller.category.Category_components;


public interface Game_components extends Category_components {

    OnClickListener locked_On_click = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast toast = Toast.makeText(v.getContext(), "⚠️Locked\n\nPlease purchase Lessons with coins to unlock the game for this category!", (int)10000);
            View view = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view.getBackground().setColorFilter(Color.parseColor("#ffc3bf"), PorterDuff.Mode.SRC_IN);

            //Gets the TextView from the Toast so it can be editted
            TextView text = view.findViewById(android.R.id.message);
            text.setTextColor(Color.parseColor("#343A40"));
            text.setGravity(Gravity.CENTER);

            toast.show();
        }
    };


    //so interface can save variable values.
    

}

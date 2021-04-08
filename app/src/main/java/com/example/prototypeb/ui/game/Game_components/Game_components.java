package com.example.prototypeb.ui.game.Game_components;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototypeb.controller.category.Category_components;
import com.example.prototypeb.controller.toast.Alert_toast;


public interface Game_components extends Category_components {

    OnClickListener locked_On_click = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Alert_toast alert_toast = new Alert_toast(v.getContext(),"⚠️Locked\n\nPlease purchase Lessons with coins to unlock the game for this category!");
            alert_toast.show_toast();
        }
    };

    public boolean check_all_categories_passed();
    //so interface can save variable values.
}

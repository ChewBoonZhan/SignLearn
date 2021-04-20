package com.example.prototypeb.controller.lesson_unlocking;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototypeb.controller.toast.Alert_toast;

public class Lesson_cannot_be_unlocked {
    // context for creating alert toast
    private Context lesson_cannot_unlock_context;

    // alert toast to let user know category cannot be unlocked
    private Alert_toast alert_toast;

    /**
     * Constructor
     * @param context - context to show alert toast
     * @param score_required - score required to unlock category
     */
    public Lesson_cannot_be_unlocked(Context context,int score_required){
        lesson_cannot_unlock_context = context;

        // create alert toast to let user know category cannot be unlocked due to lack of score
        alert_toast = new Alert_toast(lesson_cannot_unlock_context,"⚠  You can't unlock this lesson yet. \nPoints needed: " + score_required);

    }

    /**
     * Show alert toast to user
     */
    public void show_toast(){
        alert_toast.show_toast();
    }
}

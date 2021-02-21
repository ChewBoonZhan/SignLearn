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
    private Context lesson_cannot_unlock_context;
    private Alert_toast alert_toast;
    public Lesson_cannot_be_unlocked(Context context,int score_required){
        lesson_cannot_unlock_context = context;
        alert_toast = new Alert_toast(lesson_cannot_unlock_context,"⚠  You can't unlock this lesson yet. \nPoints needed: " + score_required);

    }
    public void show_toast(){
        alert_toast.show_toast();
    }
}

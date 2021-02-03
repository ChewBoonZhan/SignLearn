package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.ui.lesson.Lesson_components.Adverbs.No;
import com.example.prototypeb.ui.lesson.Lesson_components.Adverbs.Yes;

public class Adverbs extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adverbs_menu);

        //telling the button what to do
        //Yes button
        button = (Button) findViewById(R.id.yes_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), Yes.class);
                startActivity(intent);
            }
        });
        //No button
        button = (Button) findViewById(R.id.no_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), No.class);
                startActivity(intent);
            }
        });


    }

}
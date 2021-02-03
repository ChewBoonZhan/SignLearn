package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.ui.lesson.Lesson_components.Alphabets.A;
import com.example.prototypeb.ui.lesson.Lesson_components.Alphabets.B;
import com.example.prototypeb.ui.lesson.Lesson_components.Alphabets.C;
import com.example.prototypeb.ui.lesson.Lesson_components.Alphabets.Y;

public class Alphabets extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alphabets_menu);

        //telling the button what to do
        //A button
        button = (Button) findViewById(R.id.a_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), A.class);
                startActivity(intent);
            }
        });
        //B button
        button = (Button) findViewById(R.id.b_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), B.class);
                startActivity(intent);
            }
        });
        //C button
        button = (Button) findViewById(R.id.c_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), C.class);
                startActivity(intent);
            }
        });
        //Y button
        button = (Button) findViewById(R.id.y_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), Y.class);
                startActivity(intent);
            }
        });



    }
}
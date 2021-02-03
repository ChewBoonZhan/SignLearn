package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.ui.lesson.Lesson_components.Numbers.Num1;
import com.example.prototypeb.ui.lesson.Lesson_components.Numbers.Num2;
import com.example.prototypeb.ui.lesson.Lesson_components.Numbers.Num10;

public class Numbers extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numbers_menu);

        //telling the button what to do
        //1 button
        button = (Button) findViewById(R.id.num1_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), Num1.class);
                startActivity(intent);
            }
        });
        //2 button
        button = (Button) findViewById(R.id.num2_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), Num2.class);
                startActivity(intent);
            }
        });
        //3 button
        button = (Button) findViewById(R.id.num10_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), Num10.class);
                startActivity(intent);
            }
        });
    }
}
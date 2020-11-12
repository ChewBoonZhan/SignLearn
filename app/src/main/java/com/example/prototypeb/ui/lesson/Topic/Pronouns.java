package com.example.prototypeb.ui.lesson.Topic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.ui.lesson.Pronouns.Me;
import com.example.prototypeb.ui.lesson.Pronouns.You;

public class Pronouns extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pronouns_menu);

        //telling the button what to do
        //I button
        button = (Button) findViewById(R.id.me_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), Me.class);
                startActivity(intent);
            }
        });
        //You button
        button = (Button) findViewById(R.id.you_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), You.class);
                startActivity(intent);
            }
        });

    }
}
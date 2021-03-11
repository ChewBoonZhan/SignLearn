package com.example.prototypeb.controller.sub_action_bar;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototypeb.R;

public abstract class Sub_action_bar extends AppCompatActivity {
    private TextView title_text;
    private ImageButton back_button;
    public void get_screen_elements(){
        title_text = findViewById(R.id.screen_title);
        back_button = findViewById(R.id.back_button);
    }
    private void back_button_pressed(){
        super.onBackPressed();
    }
    public void set_back_button_onclick(){
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_button_pressed();
            }
        });
    }
    public void set_title_text(String title){
        title_text.setText(title);
    }
    public void set_title_text(int title_id){
        title_text.setText(title_id);
    }

}

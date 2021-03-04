package com.example.prototypeb.ui.registration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.prototypeb.MainActivity;
import com.example.prototypeb.R;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.ui.home.HomeFragment;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Adverbs;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegistrationFragment extends AppCompatActivity {
    private ArrayList<ImageButton> user_icon;
    private EditText first_name,last_name;
    private ImageButton completed_register;
    private int button_selected_index= 0;
    private final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z ]{2,10}$");
    private final int MIN_NAME_LENGTH = 2;

    private File_connections file_connections;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);
        file_connections = new File_connections(this);
        get_screen_elements();
        set_user_icon_on_click();
        set_complete_register_on_click();
        set_button_tag();
    }
    private void set_button_tag(){

        user_icon.get(0).setTag(R.drawable.male_icon);
        user_icon.get(1).setTag(R.drawable.female_icon);
        user_icon.get(2).setTag(R.drawable.fish_icon);
        user_icon.get(3).setTag(R.drawable.bear_icon);
        user_icon.get(4).setTag(R.drawable.thor_icon);
        user_icon.get(5).setTag(R.drawable.iron_man_icon);

    }
    private void get_screen_elements(){
        get_user_icon();
        get_user_name();
        completed_register = findViewById(R.id.register_complete);
    }
    private void set_complete_register_on_click(){
        completed_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                check_name_complete();
            }
        });
    }
    private void check_name_complete(){
        if((!name_has_error(first_name,"First Name",5)) &&(!name_has_error(last_name,"Last Name",10))){
            String name = first_name.getText().toString() + " " + last_name.getText().toString();


            file_connections.set_user_name(name);
            file_connections.set_user_icon((int)user_icon.get(button_selected_index).getTag());
            new Handler().postDelayed(new Runnable(){

                @Override
                public void run() {
                    Intent intent = new Intent(RegistrationFragment.this, HomeFragment.class);
                    startActivity(intent);
                    finish();
                }
            },1000);


        }
    }
    private boolean name_has_error(EditText name, String field_name,int max_name_length){
        String name_field_value = name.getText().toString();
        String name_field_value_trimmed = name_field_value.trim();
        if(name_field_value.isEmpty() || name_field_value_trimmed.isEmpty()){
            name.setError(field_name + " cannot be empty.");
            return true;
        }
        else if(name_field_value.length() < MIN_NAME_LENGTH || name_field_value_trimmed.length() < MIN_NAME_LENGTH){
            name.setError(field_name + " must be longer than "+MIN_NAME_LENGTH + " characters.");
            return true;
        }
        else if(name_field_value.length() > max_name_length || name_field_value_trimmed.length() > max_name_length){
            name.setError(field_name + " must be shorter than "+max_name_length + " characters.");
            return true;
        }
        else if(!NAME_PATTERN.matcher(name_field_value).matches()){
            name.setError(field_name + " must only contain Characters and Spaces.");
            return true;
        }
        return false;
    }
    private void get_user_name(){
        first_name = findViewById(R.id.register_first_name);
        last_name = findViewById(R.id.register_last_name);
    }
    private void get_user_icon(){
        user_icon = new ArrayList<ImageButton>();
        user_icon.add(findViewById(R.id.male_icon));
        user_icon.add(findViewById(R.id.female_icon));
        user_icon.add(findViewById(R.id.fish_icon));
        user_icon.add(findViewById(R.id.bear_icon));
        user_icon.add(findViewById(R.id.thor_icon));
        user_icon.add(findViewById(R.id.ironman_icon));
    }
    private void set_user_icon_on_click(){
        int length = user_icon.size();
        for(int i = 0;i<length;i++){
            user_icon.get(i).setOnClickListener(user_icon_onclick);
        }
    }
    private View.OnClickListener user_icon_onclick= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            handle_user_icon_selected(v);

        }
    };
    private void handle_user_icon_selected(View v){
        user_icon.get(button_selected_index).setBackgroundColor(Color.TRANSPARENT);
        switch(v.getId()){
            case (R.id.male_icon):{
                button_selected_index = 0;
                break;
            }
            case (R.id.female_icon):{
                button_selected_index = 1;
                break;
            }
            case (R.id.fish_icon):{
                button_selected_index = 2;
                break;
            }
            case (R.id.bear_icon):{
                button_selected_index = 3;
                break;
            }
            case (R.id.thor_icon):{
                button_selected_index = 4;
                break;
            }
            case (R.id.ironman_icon):{
                button_selected_index = 5;
                break;
            }
        }
        int background_color = ContextCompat.getColor(this, R.color.primary);
        user_icon.get(button_selected_index).setBackgroundColor(background_color);
    }
}

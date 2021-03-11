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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.prototypeb.MainActivity;
import com.example.prototypeb.R;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.controller.new_screen.New_screen;
import com.example.prototypeb.controller.valid_name.Check_valid_name;
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
    private final int DELAY_BEFORE_HOME_SCREEN = 500;
    private New_screen new_screen;
    private File_connections file_connections;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);
        new_screen = new New_screen(DELAY_BEFORE_HOME_SCREEN,this,this);
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
        if((!name_has_error(first_name,5)) &&(!name_has_error(last_name,10))){
            disable_screen_elements();
            String name = first_name.getText().toString() + " " + last_name.getText().toString();


            file_connections.set_user_name(name);
            file_connections.set_user_icon((int)user_icon.get(button_selected_index).getTag());
            new_screen.go_to_new_screen("com.example.prototypeb.ui.home.HomeFragment",true);


        }
    }
    private boolean name_has_error(EditText name,int max_name_length){
        Check_valid_name check_valid_name = new Check_valid_name();

        String name_field_value = name.getText().toString();
        if(check_valid_name.check_valid_name(name_field_value,MIN_NAME_LENGTH,max_name_length)){
            name.setError(name.getHint() + check_valid_name.getError_message());
            return true;
        }
        else{
            return false;
        }
    }
    private void disable_screen_elements(){
        first_name.setEnabled(false);
        last_name.setEnabled(false);
        completed_register.setEnabled(false);
        disable_user_button();
    }
    private void disable_user_button(){
        int length = user_icon.size();
        for(int i = 0;i<length;i++){
            user_icon.get(i).setEnabled(false);
        }
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

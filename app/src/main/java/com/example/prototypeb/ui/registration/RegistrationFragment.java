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
    // arraylist of user_icon for user to choose from
    private ArrayList<ImageButton> user_icon;

    // edittext that allows user to key in their first and last name
    private EditText first_name,last_name;

    // button that allows user to click once they have completed registration
    private ImageButton completed_register;

    // initially user_icon 0 is selected by default
    private int button_selected_index= 0;

    // pattern to compare and make sure name are text and space only.
    private final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z ]{2,10}$");

    // min length of both first and last name are 2
    private final int MIN_NAME_LENGTH = 2;

    // delay after user click "Go" is 0 seconds
    private final int DELAY_BEFORE_HOME_SCREEN = 0;

    // custom class that handles going to new screen
    private New_screen new_screen;

    // handles saving data to file, as well as retrieving data from file.
    private File_connections file_connections;

    /**
     * This function is called by default when elements on screen is created already.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the screen to be registration screen
        setContentView(R.layout.user_registration);

        //creating instance of new_screen to handle going to a new screen
        new_screen = new New_screen(DELAY_BEFORE_HOME_SCREEN,this,this);

        //creating new instance of file_connection to store data
        file_connections = new File_connections(this);

        //calling other setup functions.
        get_screen_elements();
        set_user_icon_on_click();
        set_complete_register_on_click();
        set_button_tag();

    }


    /**
     * Sets user's custom icon's tag so that app can know which button is pressed.
     */
    private void set_button_tag(){

        user_icon.get(0).setTag(R.drawable.male_icon);
        user_icon.get(1).setTag(R.drawable.female_icon);
        user_icon.get(2).setTag(R.drawable.fish_icon);
        user_icon.get(3).setTag(R.drawable.bear_icon);
        user_icon.get(4).setTag(R.drawable.thor_icon);
        user_icon.get(5).setTag(R.drawable.iron_man_icon);

    }

    /**
     * getting elements from the screen, such as user's custom icons and
     * completed register imagebutton
     */
    private void get_screen_elements(){

        //getting user's custom icons and user names.
        get_user_icon();
        get_user_name();

        //getting the imagebutton for completion of registration
        completed_register = findViewById(R.id.register_complete);
    }

    /**
     * setup such that once imagebutton "Go" is clicked, user's name is first checked if it is valid or not.
     */
    private void set_complete_register_on_click(){
        completed_register.setOnClickListener(new View.OnClickListener() {
            // set onclick for imagebutton "Go" to check user's name
            @Override
            public void onClick(View v) {
                check_name_complete();
            }
        });
    }

    /**
     * Check if user's first name and last name as error or not.
     * If the names has no error, user is redirected to main menu.
     * User's name as well as user icon is also saved in file_connections
     */
    private void check_name_complete(){
        // check if user's first name and last name has error
        if((!name_has_error(first_name,5)) &&(!name_has_error(last_name,10))){

            //disable user from interacting with screen elements while backend processes
            disable_screen_elements();

            // getting user's full name
            String name = first_name.getText().toString() + " " + last_name.getText().toString();

            // saving user's name to file connection
            file_connections.set_user_name(name);

            //saving user's custom icon to file connection
            file_connections.set_user_icon((int)user_icon.get(button_selected_index).getTag());

            //going to the main screen of the app.
            new_screen.go_to_new_screen("com.example.prototypeb.ui.home.HomeFragment",true);


        }
    }

    /**
     * checks if the given edittext's input has error in name, and meets the requirements of a valid name
     * @param name - edittext to check if its input is a valid name
     * @param max_name_length - max length of the name allowed in the edittext
     * @return valid_name - if the name in the edittext is valid or not.
     */
    private boolean name_has_error(EditText name,int max_name_length){

        //create instance of function responsible for checking if name has error
        Check_valid_name check_valid_name = new Check_valid_name();

        // getting the edittext's value
        String name_field_value = name.getText().toString();


        if(!check_valid_name.check_valid_name(name_field_value,MIN_NAME_LENGTH,max_name_length)){
            // if the name is not a valid name

            //set the error box beside the edittext section
            name.setError(name.getHint() + check_valid_name.getError_message());
            return true;
        }
        else{
            //if the name is a valid name indeed
            return false;
        }
    }

    /**
     * disable and disallows user to interact with elements on the screen
     */
    private void disable_screen_elements(){
        //user cannot key in first name and last name anymore
        first_name.setEnabled(false);
        last_name.setEnabled(false);

        //user cannot click "Go" again
        completed_register.setEnabled(false);

        //User cannot choose a different user icon anymore
        disable_user_button();
    }

    /**
     * User cannot choose a different user icon anymore.
     * Disables user from interacting with the user icons
     */
    private void disable_user_button(){
        int length = user_icon.size();
        for(int i = 0;i<length;i++){
            //disabling each user_icon buttons
            user_icon.get(i).setEnabled(false);
        }
    }

    /**
     * gets Edittext from the screen of first name and last name
     */
    private void get_user_name(){
        first_name = findViewById(R.id.register_first_name);
        last_name = findViewById(R.id.register_last_name);
    }

    /**
     * get all user's icon from the screen for processing
     */
    private void get_user_icon(){
        user_icon = new ArrayList<ImageButton>();

        //adds user ucons to an arraylist
        user_icon.add(findViewById(R.id.male_icon));
        user_icon.add(findViewById(R.id.female_icon));
        user_icon.add(findViewById(R.id.fish_icon));
        user_icon.add(findViewById(R.id.bear_icon));
        user_icon.add(findViewById(R.id.thor_icon));
        user_icon.add(findViewById(R.id.ironman_icon));
    }

    /**
     * set user icons onclick to call user_icon_on_click
     */
    private void set_user_icon_on_click(){
        int length = user_icon.size();
        for(int i = 0;i<length;i++){
            user_icon.get(i).setOnClickListener(user_icon_onclick);
        }
    }

    // set the on_click to be calling handle_user_icon_selected
    private View.OnClickListener user_icon_onclick= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            handle_user_icon_selected(v);

        }
    };

    /**
     * This function is called whenever a user icon is clicked.
     * When a user icon is clicked, the previous selected user icon will have transparent background colour
     * The selected user icon will have the primary colour
     * @param v- user icon user clicked on
     */
    private void handle_user_icon_selected(View v){
        //setting the previous user icon to have transparent colour.
        user_icon.get(button_selected_index).setBackgroundColor(Color.TRANSPARENT);

        //switch for setting button_selected_index
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

        //setting selected user icon to be primary colour
        int background_color = ContextCompat.getColor(this, R.color.primary);
        user_icon.get(button_selected_index).setBackgroundColor(background_color);
    }
}

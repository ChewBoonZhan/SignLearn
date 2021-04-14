package com.example.prototypeb.ui.game.Game_components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.app_data.Category_elements;

import java.util.ArrayList;

public class Game_attachments extends Game_screen {
    //Adverbs game context
    private Context attachments_context;
    //put Attachment sign languages as string in arrays
    private ArrayList<String> signLang = new ArrayList<String>();
    //declare app data index
    private int app_data_index = 2;
    public Game_attachments(){
        super();
        init_syllabus(app_data_index);
    }

    /**
     * Initialize attachment syllabus
     * @param context
     */
    public Game_attachments(Context context){
        super();
        attachments_context = context;
        init_syllabus(app_data_index);
    }

    /**
     * Method called upon entering Adverbs game
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Resume action
     */
    @Override
    protected void onResume() {
        super.onResume();
        super.setContext(this);

    }

    /**
     * method is called upon clicking category if category has been unlocked
     * @return on_unlocked_click
     */
    public View.OnClickListener get_unlocked_On_click(){

        return on_unlocked_click;
    }

    /**
     * Attachments game activity displayed
     */
    public View.OnClickListener get_locked_On_click(){
        return locked_On_click;
    }
    private View.OnClickListener on_unlocked_click= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), Game_attachments.class);
            attachments_context.startActivity(intent);


        }
    };


}

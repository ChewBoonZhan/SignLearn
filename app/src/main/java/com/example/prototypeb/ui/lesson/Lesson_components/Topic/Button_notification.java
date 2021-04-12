package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.prototypeb.controller.app_data.Category_elements;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;

import java.util.ArrayList;

public abstract class Button_notification extends Sub_action_bar implements Lesson_topics{
    public ArrayList<TextView> notifi_text;
    public ArrayList<String> category_elements;
    private Category_elements all_category_elements;
    public void init_category_elements(){
        all_category_elements = new Category_elements();
        category_elements = all_category_elements.getCategory_elements().get(toString());
    }
    public void set_notifi_text_visible(Context context){
        File_connections file_connections = new File_connections(context);
        int length = notifi_text.size();
        for(int i = 0;i<length;i++){
            if(!all_category_elements.check_element_not_testable(category_elements.get(i))){
                if(file_connections.check_lesson_learnt(category_elements.get(i).toLowerCase())){
                    notifi_text.get(i).setVisibility(View.GONE);
                }

            }
        }
    }
    

}

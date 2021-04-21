package com.example.prototypeb.controller.lesson_screen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.Category_elements;
import com.example.prototypeb.controller.app_data.Intent_key;
import com.example.prototypeb.controller.choice_message.One_choice_message;
import com.example.prototypeb.controller.choice_message.Two_choice_message;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.controller.toast.Alert_toast;
import com.example.prototypeb.controller.translator_verify.Translator_verify;

import pl.droidsonroids.gif.GifImageView;

public class Lesson_screen extends Sub_action_bar {
    // to display gif to user
    private GifImageView gifImageView;

    // to display title of the syllabus taight to user
    private TextView lesson_text_title;

    // to display the information of syllabus on screen to user
    private TextView lesson_info_defcontent;

    // button to allow user to check and verify syllabus learnt
    private Button check_translator_info_lesson;

    // id for displaying
    private int title_text_id;

    // intent key to get intent data
    private Intent_key intent_key = new Intent_key();

    // category elements to get syllabus of categories
    private Category_elements category_elements = new Category_elements();

    /**
     * This function is called after elements on the screen are created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // set the view as infor lesson for user to learn more about the syllabus
        setContentView(R.layout.info_lesson);

        //get elements on the screen for processing
        get_components_screen();

        // set the components on screen to the correct value based on syllabus user chose to learn
        set_components_value();

        // get components on screen for sub_action_bar
        get_screen_elements();

        // set back button for sub_action_bar
        set_back_button_onclick();

        // title text for sub_action_bar based on syllabus user is in
        set_title_text(title_text_id);
    }

    /**
     * Set component acquired from screen's value
     */
    private void set_components_value(){
        // get the intent data to the screen
        Lesson_screen_components lesson_screen_components = (Lesson_screen_components) getIntent().getSerializableExtra(intent_key.getScreen_component());

        // get title text id for sylalbus on screen
        title_text_id = lesson_screen_components.get_title_id();

        // set the lesson_screen's content data
        lesson_info_defcontent.setText(lesson_screen_components.get_content_id());

        // set the title text for the syllabus of the category
        lesson_text_title.setText(title_text_id);

        // set the gif of the sylalbus to be viewed and learnt by user
        gifImageView.setImageResource(lesson_screen_components.get_gif_id());

        // set translator verify button onclick
        set_translator_button_on_click();

    }

    /**
     * Set translator verify for syllabus onclick
     */
    private void set_translator_button_on_click(){
        // get the data tor translator label or syllabus from intent
        String correct_translator_label = getIntent().getStringExtra(intent_key.getTranslator_label());

        // get the data for lesson_topic
        String translator_lesson_topics = getIntent().getStringExtra(intent_key.getTranslator_lesson_topics());
        View.OnClickListener check_on_click = null;

        // check if the element can be tested using the translator
        if(category_elements.check_element_not_testable(correct_translator_label)){

           Lesson_screen lesson_screen = this;
           // set verify_translator onclick to show dialog interface that lesson cannot be learnt
            check_on_click = new View.OnClickListener() {

                /**
                 * Dismiss the dialog on click of the positive choice
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener positive_choice = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    };
                    // create one_choice_message
                    One_choice_message one_choice_message = new One_choice_message(lesson_screen,"Content Unavailable.","The verification feature for this category will be released in the upcoming update! We apologize for any difficulties or misunderstanding caused.","Okay.",positive_choice);

                    // show the message to the user
                    one_choice_message.show_message();
                }
            };
        }
        else{
            // lesson can be verified using translator
            check_on_click = new View.OnClickListener() {
                /**
                 * Start new intent when verify translator is clicked to go to new translator verify screen
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Translator_verify.class)
                            .putExtra(intent_key.getTranslator_screen_title_id(),title_text_id)
                            .putExtra(intent_key.getTranslator_label(),correct_translator_label)
                            .putExtra(intent_key.getTranslator_lesson_topics(),translator_lesson_topics)

                    );
                }
            };
        }
        // set onclick listener of check_translator to check_on_click set
        check_translator_info_lesson.setOnClickListener(check_on_click);
    }

    /**
     * Get related components from screen to be processed
     */
    private void get_components_screen(){
        // gifview to insert gif based on syllabus
        gifImageView = findViewById(R.id.lesson_gif_imageView);

        // lesson text title to insert title based on the syllabus
        lesson_text_title = findViewById(R.id.lesson_text_title);

        // lesson text content to insert content based on syllabus
        lesson_info_defcontent = findViewById(R.id.lesson_info_defcontent);

        // get button for translator verify to set onclick for it.
        check_translator_info_lesson = findViewById(R.id.check_translator_info_lesson);
    }




}

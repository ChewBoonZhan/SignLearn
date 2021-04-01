package com.example.prototypeb.custom_matcher.matcher;

import android.view.View;
import android.widget.TextView;

import com.example.prototypeb.controller.app_data.Category_elements;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;

public class Spinner_change_category_test extends TypeSafeMatcher<View> {
    private int category_index;
    private Category_elements category_elements = new Category_elements();
    public void set_category_index(int category_index) {
        this.category_index = category_index;
    }

    @Override
    protected boolean matchesSafely(View item) {
        TextView textView = (TextView) item;
        String value = textView.getText().toString();
        ArrayList <String> testable_elements = category_elements.get_category_elements_testable(category_index);
        return testable_elements.contains(value);

    }

    @Override
    public void describeTo(Description description) {

    }

}

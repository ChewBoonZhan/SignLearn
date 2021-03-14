package com.example.prototypeb.custom_matcher.matcher;

import android.view.View;

import androidx.annotation.ColorRes;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.example.prototypeb.custom_matcher.matcher_support.BackgroundColourMatcher;

import org.hamcrest.Matcher;

import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static org.hamcrest.Matchers.allOf;

public class View_tests {
    public static ViewAction forceClick() {
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return allOf(isClickable(), isEnabled(), isDisplayed());
            }

            @Override public String getDescription() {
                return "force click";
            }

            @Override public void perform(UiController uiController, View view) {
                view.performClick(); // perform click without checking view coordinates.
                uiController.loopMainThreadUntilIdle();
            }
        };
    }

    public static Matcher<View> testBackgroundColour(@ColorRes int expectedColor) {
        return new BackgroundColourMatcher(expectedColor);
    }
}

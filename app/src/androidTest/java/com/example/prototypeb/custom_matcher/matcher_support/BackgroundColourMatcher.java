package com.example.prototypeb.custom_matcher.matcher_support;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.core.content.res.ResourcesCompat;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class BackgroundColourMatcher extends TypeSafeMatcher<View> {

    @ColorRes
    private final int mExpectedColourResId;

    private int mColorFromView;

    public BackgroundColourMatcher(@ColorRes int expectedColourResId) {
        super(View.class);
        mExpectedColourResId = expectedColourResId;
    }

    @Override
    protected boolean matchesSafely(View item) {

        if (item.getBackground() == null) {
            return false;
        }
        Resources resources = item.getContext().getResources();
        int colourFromResources = ResourcesCompat.getColor(resources, mExpectedColourResId, null);
        mColorFromView = ((ColorDrawable) item.getBackground()).getColor();
        return mColorFromView == colourFromResources;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Color did not match " + mExpectedColourResId + " was " + mColorFromView);
    }
}
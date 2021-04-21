package com.example.prototypeb.controller.category;

import android.view.View;

public interface Category_components {
    /**
     * Get category which are unlocked's onclick
     * @return on_click_unlocked - category buttons which are unlocked's onclick
     */
    public View.OnClickListener get_unlocked_On_click();

    /**
     * Get category which are locked's onclick method
     * @return on_click_locked - category button which are locked's click
     */
    public View.OnClickListener get_locked_On_click();
}

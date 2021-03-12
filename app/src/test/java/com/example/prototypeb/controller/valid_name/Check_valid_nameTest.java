package com.example.prototypeb.controller.valid_name;

import junit.framework.TestCase;

public class Check_valid_nameTest extends TestCase {
    private Check_valid_name check_valid_name = new Check_valid_name();
    public void testCheck_valid_name() {
        assertEquals(check_valid_name.check_valid_name("valid name",1,10),true);
        assertEquals(check_valid_name.getError_message(),"");
        int maxlength = 8;
        assertEquals(check_valid_name.check_valid_name("valid name",1,maxlength),false);
        assertEquals(check_valid_name.getError_message()," must be shorter than "+maxlength+" characters.");
        int minlength = 3;
        assertEquals(check_valid_name.check_valid_name("va",minlength,maxlength),false);
        assertEquals(check_valid_name.getError_message()," must be longer than " + minlength + " characters.");

        assertEquals(check_valid_name.check_valid_name("",minlength,maxlength),false);
        assertEquals(check_valid_name.getError_message()," cannot be empty.");

        assertEquals(check_valid_name.check_valid_name("   ",minlength,maxlength),false);
        assertEquals(check_valid_name.getError_message()," cannot be empty.");
        maxlength = 20;
        assertEquals(check_valid_name.check_valid_name("12dsre",minlength,maxlength),false);
        assertEquals(check_valid_name.getError_message()," must only contain Characters and Spaces.");

        assertEquals(check_valid_name.check_valid_name("!@#$%^&*()",minlength,maxlength),false);
        assertEquals(check_valid_name.getError_message()," must only contain Characters and Spaces.");

        assertEquals(check_valid_name.check_valid_name("-_=+|]}[{,",minlength,maxlength),false);
        assertEquals(check_valid_name.getError_message()," must only contain Characters and Spaces.");

        assertEquals(check_valid_name.check_valid_name("  hello  ",minlength,maxlength),true);
        assertEquals(check_valid_name.getError_message(),"");

    }

    public void testGetError_message() {

        assertEquals(check_valid_name.getError_message(),"");
    }
}
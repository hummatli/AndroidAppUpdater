package com.mobapphome.mahandroidupdater;

/**
 * Created by settar on 7/26/16.
 */

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.mobapphome.mahandroidupdater.sample.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MAHButtonClickTest {

    //private String mStringToBetyped;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
       // mStringToBetyped = "Espresso";
    }

    @Test
    public void clickButton() {
        // Type text and then press the button.
//        onView(withId(R.id.editTextUserInput))
//                .perform(ViewActions.typeText(mStringToBetyped), closeSoftKeyboard());
//        onView(withId(R.id.changeTextBt)).perform(ViewActions.click());
//
//        // Check that the text was changed.
//        onView(withId(R.id.textToBeChanged))
//                .check(ViewAssertions.matches(withText(mStringToBetyped)));

        onView(withText("Test open Updater Dlg"))            // withId(R.id.my_view) is a ViewMatcher
                .perform(ViewActions.click())               // click() is a ViewAction
               // .check(ViewAssertions.matches(isDisplayed())) // matches(isDisplayed()) is a ViewAssertion
                ;


    }

    @Test
    public void clickButton2() {
        // Type text and then press the button.
//        onView(withId(R.id.editTextUserInput))
//                .perform(ViewActions.typeText(mStringToBetyped), closeSoftKeyboard());
//        onView(withId(R.id.changeTextBt)).perform(ViewActions.click());
//
//        // Check that the text was changed.
//        onView(withId(R.id.textToBeChanged))
//                .check(ViewAssertions.matches(withText(mStringToBetyped)));


        onView(withText("Test open Restricter Dlg"))            // withId(R.id.my_view) is a ViewMatcher
                .perform(ViewActions.click())               // click() is a ViewAction
                //.check(ViewAssertions.matches(isDisplayed())) // matches(isDisplayed()) is a ViewAssertion
                ;

    }
}

package com.project.guessthatchord;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule= new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mActivity=null;

    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mActivity=null;
    }

    @Test
    public void buttonsAreDisplayed() {
        onView(withId(R.id.playButton)).check(matches(isDisplayed()));
        onView(withId(R.id.optionA)).check(matches(isDisplayed()));
        onView(withId(R.id.optionB)).check(matches(isDisplayed()));
        onView(withId(R.id.optionC)).check(matches(isDisplayed()));
        onView(withId(R.id.optionD)).check(matches(isDisplayed()));
    }

    @Test
    public void buttonsAreClickable(){
        onView(withId(R.id.playButton)).check(matches(isClickable()));
        onView(withId(R.id.optionA)).check(matches(isClickable()));
        onView(withId(R.id.optionB)).check(matches(isClickable()));
        onView(withId(R.id.optionC)).check(matches(isClickable()));
        onView(withId(R.id.optionD)).check(matches(isClickable()));
    }
}
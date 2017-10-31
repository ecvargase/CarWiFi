package com.espiot.cav.carwifi;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {

        ViewInteraction dir = onView(
                allOf(withId(R.id.joys), isDisplayed()));
        dir.check(matches(isDisplayed()));

        ViewInteraction vel = onView(
                allOf(withId(R.id.seek_arc), isDisplayed()));
        vel.check(matches(isDisplayed()));

        ViewInteraction toggleButton = onView(
                allOf(withId(R.id.toggleButton),
                        isDisplayed()));
        toggleButton.check(matches(isDisplayed())).perform(click());

        ViewInteraction toggleButton2 = onView(
                allOf(withId(R.id.toggleButton), isDisplayed()));
        toggleButton2.check(matches(isDisplayed())).perform(click());

    }
}

package org.mozilla.focus.activity;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mozilla.focus.helpers.TestHelper;

import java.io.IOException;

import static android.support.test.espresso.action.ViewActions.click;
import static org.mozilla.focus.fragment.FirstrunFragment.FIRSTRUN_PREF;
import static org.mozilla.focus.helpers.TestHelper.waitingTime;
import static org.mozilla.focus.helpers.TestHelper.webPageLoadwaitingTime;

@RunWith(AndroidJUnit4.class)
public class NimbledroidSiteLoadTest {

    // 5 Top mobile sites for the US and Germany from Quantcast's list:
    private String[] websites = {
            "wikia.com",
            "buzzfeed.com",
            "yelp.de",
            "Eurosport.eu",
            "Ranker.com"
    };

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<MainActivity>(MainActivity.class) {

        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();

            Context appContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext()
                    .getApplicationContext();

            PreferenceManager.getDefaultSharedPreferences(appContext)
                    .edit()
                    .putBoolean(FIRSTRUN_PREF, true)
                    .apply();
        }
    };

    @After
    public void tearDown() throws Exception {
        mActivityTestRule.getActivity().finishAndRemoveTask();
    }

    @Test
    public void NBSiteLoadTest() throws InterruptedException, UiObjectNotFoundException, IOException {

        for (int i = 0; i < websites.length; i++) { // Load websites to measure loading time with Nimbledroid service
            TestHelper.inlineAutocompleteEditText.waitForExists(waitingTime);
            TestHelper.inlineAutocompleteEditText.clearTextField();
            TestHelper.inlineAutocompleteEditText.setText(websites[i]);
            TestHelper.hint.waitForExists(waitingTime);
            TestHelper.pressEnterKey();
            TestHelper.waitForWebContent();
            TestHelper.waitForIdle();
            TestHelper.progressBar.waitUntilGone(webPageLoadwaitingTime);

            // Delete the page
            TestHelper.floatingEraseButton.perform(click());
            TestHelper.inlineAutocompleteEditText.waitForExists(waitingTime);
        }
    }
}

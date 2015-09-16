package org.michaelevans.espresso.intents.sample;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class StartDialerActivityTest {

  private static final String VALID_PHONE_NUMBER = "281-330-8004";
  private static final Uri INTENT_DATA_PHONE_NUMBER = Uri.parse("tel:" + VALID_PHONE_NUMBER);

  @Rule public IntentsTestRule<StartDialerActivity> activityRule =
      new IntentsTestRule<>(StartDialerActivity.class);

  /**
   * By default, Espresso Intents won't stub your intents. Since we just want to verify that the
   * phone intent was started, let's just have all intents return {@value Activity#RESULT_OK}.
   */
  @Before public void stubAllExternalIntents() {
    intending(hasAction(Intent.ACTION_DIAL)).respondWith(
        new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
  }

  @Test public void typeNumber_ValidInput_InitiatesCall() {
    onView(withId(R.id.number_input)).perform(typeText(VALID_PHONE_NUMBER), closeSoftKeyboard());
    onView(withId(R.id.dial_button)).perform(click());

    intended(allOf(hasAction(Intent.ACTION_DIAL), hasData(INTENT_DATA_PHONE_NUMBER)));
  }
}

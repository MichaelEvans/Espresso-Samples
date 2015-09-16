package org.michaelevans.espresso.intents.sample;

import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasType;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class ContactPickerActivityTest {
  private final static String TEST_URI = "foo://bar?q=123";

  @Rule public IntentsTestRule<ContactPickerActivity> activityRule =
      new IntentsTestRule<>(ContactPickerActivity.class);

  @Before public void stubContactIntent() {
    Intent intent = new Intent();
    intent.setData(Uri.parse(TEST_URI));
    ActivityResult result = new ActivityResult(Activity.RESULT_OK, intent);

    intending(allOf(
        hasData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI),
        hasAction(Intent.ACTION_PICK))
    ).respondWith(result);
  }

  @Test public void pickContact_viewIsSet() {
    //Check to make sure the Uri field is empty
    onView(withId(R.id.phone_number)).check(matches(withText("")));

    //Start contact picker
    onView(withId(R.id.pick_contact)).perform(click());

    //Verify that Uri was set properly.
    onView(withId(R.id.phone_number)).check(matches(withText(TEST_URI)));
  }
}

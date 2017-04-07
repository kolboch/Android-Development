package bochynski.karol.bmi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers.Visibility;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by Karol on 2017-03-27.
 */

public class ViewTests {
    private Editor preferencesEditor;
    private Intent intent;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, true, false); // false to not run immediately activity

    @Before
    public void setUp() {
        intent = new Intent();
        Context context = getInstrumentation().getTargetContext();
        preferencesEditor = context.getSharedPreferences(MainActivity.PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
    }

    @Test
    public void checkMetricInputViews() {
        clearPreferencesAndLaunch();

        onView(withId(R.id.mass_input))
                .perform(typeText("60"), closeSoftKeyboard());
        onView(withId(R.id.height_input))
                .perform(typeText("2"), closeSoftKeyboard());
    }

    @Test
    public void changeFromMetricToImperial() {
        clearPreferencesAndLaunch();

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext()); //opening options/ overflow menu
        onView(withText(R.string.menu_units_lb_in)).perform(click());
        onView(withId(R.id.mass_input)).check(doesNotExist());
        onView(withId(R.id.height_input)).check(doesNotExist());

        onView(withId(R.id.stones_input)).check(matches(isDisplayed()));
        onView(withId(R.id.stones_label)).check(matches(isDisplayed()));
        onView(withId(R.id.pounds_input)).check(matches(isDisplayed()));
        onView(withId(R.id.pounds_label)).check(matches(isDisplayed()));
        onView(withId(R.id.inches_input)).check(matches(isDisplayed()));
        onView(withId(R.id.inches_label)).check(matches(isDisplayed()));
        onView(withId(R.id.feet_input)).check(matches(isDisplayed()));
        onView(withId(R.id.feet_label)).check(matches(isDisplayed()));
    }


    @Test
    public void checkFromImperialToMetric() {
        clearPreferencesAndLaunch();

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.menu_units_lb_in)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.menu_units_kg_m)).perform(click());
        onView(withId(R.id.mass_input)).check(matches(isDisplayed()));
        onView(withId(R.id.height_input)).check(matches(isDisplayed()));
    }

    @Test
    public void checkShowingBMIResultOnButtonClick() {
        clearPreferencesAndLaunch();

        onView(withId(R.id.mass_input))
                .perform(typeText("60"), closeSoftKeyboard());
        onView(withId(R.id.height_input))
                .perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.count_bmi_button)).perform(click());

        // not isDisplayed to pass small devices where u have to scroll to see that view
        onView(withId(R.id.BMI_result)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.BMI_result)).check(matches(withText("15"))); // BMI equation: mass / (height^2) [for metric which is initial setup]

        onView(withId(R.id.BMI_result_description)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.BMI_result_description)).check(matches(withText(R.string.bmi_result_underweight)));
    }

    @Test
    public void checkNotShowingResultViewsOnInvalidDataInput() {
        clearPreferencesAndLaunch();

        onView(withId(R.id.mass_input))
                .perform(typeText("500"), closeSoftKeyboard());
        onView(withId(R.id.height_input))
                .perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.count_bmi_button)).perform(click());

        onView(withId(R.id.BMI_result)).check(matches(not(isDisplayed())));
        onView(withId(R.id.BMI_result_description)).check(matches(not(isDisplayed())));
    }

    @Test
    public void checkShowingResultViewsFromSharedPreferences() {
        preferencesEditor.putString(MainActivity.BMI_RESULT_SAVED, "24.5");
        preferencesEditor.putString(MainActivity.BMI_RESULT_DESCRIPTION_SAVED, getResourceString(R.string.bmi_result_healthy));
        preferencesEditor.commit();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.BMI_result)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.BMI_result)).check(matches(withText("24.5")));

        onView(withId(R.id.BMI_result_description)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.BMI_result_description)).check(matches(withText(R.string.bmi_result_healthy)));
    }

    private String getResourceString(int id) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources().getString(id);
    }

    private void clearPreferencesAndLaunch() {
        preferencesEditor.clear();
        preferencesEditor.commit();
        mActivityRule.launchActivity(intent);
    }
}

package bochynski.karol.bmi;

import android.support.test.espresso.matcher.ViewMatchers.Visibility;
import android.support.test.rule.ActivityTestRule;

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
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkMetricInputViews(){
        onView(withId(R.id.mass_input))
                .perform(typeText("60"), closeSoftKeyboard());
        onView(withId(R.id.height_input))
                .perform(typeText("2"), closeSoftKeyboard());
    }

    @Test
    public void changeFromMetricToImperial(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext()); //opening options/ overflow menu
        onView(withText(R.string.units_lb_in)).perform(click());
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
    public void checkFromImperialToMetric(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.units_lb_in)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.units_kg_m)).perform(click());
        onView(withId(R.id.mass_input)).check(matches(isDisplayed()));
        onView(withId(R.id.height_input)).check(matches(isDisplayed()));
    }

    @Test
    public void checkShowingBMIResultOnButtonClick(){
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
    public void checkNotShowingResultViewsOnInvalidDataInput(){
        onView(withId(R.id.mass_input))
                .perform(typeText("500"), closeSoftKeyboard());
        onView(withId(R.id.height_input))
                .perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.count_bmi_button)).perform(click());

        onView(withId(R.id.BMI_result)).check(matches(not(isDisplayed())));
        onView(withId(R.id.BMI_result_description)).check(matches(not(isDisplayed())));
    }
}

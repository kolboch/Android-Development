package bochynski.karol.bmi;

import org.junit.Test;

import bochynski.karol.bmi.exceptions.InvalidHeightException;
import bochynski.karol.bmi.exceptions.InvalidMassException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by Karol on 2017-03-15.
 */

public class CountBMIForKgMTest {

    @Test
    public void massUnderZeroIsInvalid() throws Exception {
        float testMass = -1.0f; //GIVEN
        ICountBMI counter = new CountBMIForKgM();
        boolean isMassValid = counter.isMassValid(testMass);
        assertFalse(isMassValid);
    }

    @Test
    public void countBMITest() throws Exception {
        float mass = 100;
        float height = 1.4f;
        ICountBMI counter = new CountBMIForKgM();
        float result = counter.countBMI(mass, height);
        assertEquals(100 / (1.4f * 1.4f), result, 0.001f);
    }

    @Test(expected = InvalidHeightException.class)
    public void countBMIInvalidHeightExceptionTest() {
        float mass = 100;
        float height = 3;
        ICountBMI counter = new CountBMIForKgM();
        float result = counter.countBMI(mass, height);
    }

    @Test(expected = InvalidMassException.class)
    public void countBMIInvalidMassExceptionTest() {
        float mass = 300;
        float height = 1.2f;
        ICountBMI counter = new CountBMIForKgM();
        float result = counter.countBMI(mass, height);
    }

}

package bochynski.karol.bmi;

import org.junit.Test;

import bochynski.karol.bmi.exceptions.InvalidHeightException;
import bochynski.karol.bmi.exceptions.InvalidMassException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by Karol on 2017-03-23.
 */

public class CountBMIForLbInTest {

    @Test
    public void invalidSmallMassTest() {
        float mass = 20f;
        ICountBMI counter = new CountBMIForLbIn();
        assertFalse(counter.isMassValid(mass));
    }

    @Test
    public void invalidLargeMassTest() {
        float mass = 552f;
        ICountBMI counter = new CountBMIForLbIn();
        assertFalse(counter.isMassValid(mass));
    }

    @Test
    public void invalidSmallHeightTest() {
        float height = 18f;
        ICountBMI counter = new CountBMIForLbIn();
        assertFalse(counter.isHeightValid(height));
    }

    @Test
    public void invalidLargeHeightTest() {
        float height = 100f;
        ICountBMI counter = new CountBMIForLbIn();
        assertFalse(counter.isHeightValid(height));
    }

    @Test
    public void countBMITest() {
        float mass = 300f;
        float height = 50f;
        ICountBMI counter = new CountBMIForLbIn();
        float result = counter.countBMI(mass, height);
        assertEquals(mass / (height * height) * 703, result, 0.01f);
    }

    @Test
    public void countBMIWithFtSt() {
        float feet = 5;
        float inches = 7;
        float stones = 5;
        float pounds = 11;
        CountBMIForLbIn counter = new CountBMIForLbIn();
        float result = counter.countBMI(stones, pounds, feet, inches);
        inches += feet * CountBMIForLbIn.INCHES_IN_FOOT;
        pounds += stones * CountBMIForLbIn.POUNDS_IN_STONE;
        assertEquals(pounds / (inches * inches) * 703, result, 0.01f);
    }

    @Test(expected = InvalidHeightException.class)
    public void countBMIInvalidHeightExceptionTest() {
        float mass = 250;
        float height = 101;
        ICountBMI counter = new CountBMIForLbIn();
        float result = counter.countBMI(mass, height);
    }

    @Test(expected = InvalidMassException.class)
    public void countBMIInvalidMassExceptionTest(){
        float mass = 570; //pounds
        float height = 50; //inches
        ICountBMI counter = new CountBMIForLbIn();
        float result = counter.countBMI(mass, height);
    }


}

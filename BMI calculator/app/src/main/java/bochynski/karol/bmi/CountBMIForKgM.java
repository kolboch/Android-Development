package bochynski.karol.bmi;

import bochynski.karol.bmi.exceptions.InvalidHeightException;
import bochynski.karol.bmi.exceptions.InvalidMassException;

/**
 * Created by Karol on 2017-03-15.
 */

public class CountBMIForKgM implements ICountBMI {

    private static final float MIN_MASS = 10;
    private static final float MAX_MASS = 250;
    private static final float MIN_HEIGHT = 0.5f;
    private static final float MAX_HEIGHT = 2.5f;

    @Override
    public boolean isMassValid(float mass) {
        return (mass >= MIN_MASS && mass <= MAX_MASS);
    }

    @Override
    public boolean isHeightValid(float height) {
        return (height >= MIN_HEIGHT && height <= MAX_HEIGHT);
    }

    @Override
    public float countBMI(float mass, float height) {
        if (!isMassValid(mass)) {
            throw new InvalidMassException("Invalid mass value.");
        } else if (!isHeightValid(height)) {
            throw new InvalidHeightException("Invalid height value.");
        }
        return mass / (height * height);
    }
}

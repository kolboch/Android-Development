package bochynski.karol.bmi;

import bochynski.karol.bmi.exceptions.InvalidHeightException;
import bochynski.karol.bmi.exceptions.InvalidMassException;

/**
 * Created by Karol on 2017-03-23.
 */

public class CountBMIForLbIn implements ICountBMI {

    private static final float MIN_MASS = 22; // pounds
    private static final float MAX_MASS = 551; // pounds
    private static final float MIN_HEIGHT = 19.69f; // inches
    private static final float MAX_HEIGHT = 98.43f; //inches
    public static final float POUNDS_IN_STONE = 14f;
    public static final float INCHES_IN_FOOT = 12f;
    public static final int BMI_CONVERT_MULTIPLIER = 703;


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
        return mass / (height * height) * BMI_CONVERT_MULTIPLIER;
    }

    public float countBMI(float stones, float pounds, float feet, float inches){
        pounds += stones * POUNDS_IN_STONE;
        inches += feet * INCHES_IN_FOOT;
        return countBMI(pounds, inches);
    }
}

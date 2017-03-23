package bochynski.karol.lab1;

/**
 * Created by Karol on 2017-03-15.
 */

public interface ICountBMI {

    boolean isMassValid(float mass);

    boolean isHeightValid(float height);

    float countBMI(float mass, float height);

}

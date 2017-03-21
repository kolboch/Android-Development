package com.example.android.pets;

/**
 * Created by Karol on 2017-03-21.
 */

public class WeightNegativeException extends RuntimeException {

    public WeightNegativeException(){
        super();
    }

    public WeightNegativeException(String message){
        super(message);
    }
}

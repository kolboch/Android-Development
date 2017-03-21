package com.example.android.pets;

/**
 * Created by Karol on 2017-03-02.
 */

public class NotEnoughDataProvidedException extends RuntimeException {

    public NotEnoughDataProvidedException(){
        super();
    }

    public NotEnoughDataProvidedException(String message){
        super(message);
    }
}

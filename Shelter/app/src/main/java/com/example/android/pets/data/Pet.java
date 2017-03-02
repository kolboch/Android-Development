package com.example.android.pets.data;

/**
 * Created by Karol on 2017-03-02.
 */

public class Pet {
    private int id = -1;
    private String name;
    private String breed;
    private int gender;
    private int weight = 0;

    public Pet(String name, String breed, int gender){
        this.name = name;
        this.breed = breed;
        this.gender = gender;
    }

    public Pet(String name, String breed, int gender, int weight){
        this(name, breed, gender);
        this.weight = weight;
    }

    public Pet(int id, String name, String breed, int gender){
        this(name, breed, gender);
        this.id = id;
    }

    public Pet(int id, String name, String breed, int gender, int weight){
        this(name, breed, gender, weight);
        this.id = id;
    }

    /**
     * int, id of pet, if not specifed default -1
     */
    public int getId() {
        return id;
    }

    /**
     * @return String, name of pet
     */
    public String getName() {
        return name;
    }

    /**
     * @return String, breed of pet
     */
    public String getBreed() {
        return breed;
    }

    /**
     * @return int, gender of pet, constants specified by PetContract.PetEntry static values:
     * GENDER_MALE = 1;
     * GENDER_FEMALE = 2;
     * GENDER_UNKNOWN = 0;
     */
    public int getGender() {
        return gender;
    }

    /**
     * @return int, weight of pet, if not specified in constructor default 0
     */
    public int getWeight() {
        return weight;
    }
}

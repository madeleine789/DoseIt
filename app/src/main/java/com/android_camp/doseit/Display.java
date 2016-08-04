package com.android_camp.doseit;

import static com.android_camp.doseit.ConversationHandler.*;

public class Display {

    public States state;
    public Params input;

    public double weight;
    public Gender gender;
    public Age age;


    enum Gender {FEMALE, MALE, NONE}
    enum Age { ADULT, MINOR, NONE}

    public Display(){

        this.state = States.PARAMETERS;
        this.input = Params.GENDER;
        this.gender = Gender.NONE;
        this.age = Age.NONE;
        this.weight = 0;

    }


    public String getCurrentValue() {
        switch (this.input){
            case AGE:
                return age.toString();
            case GENDER:
                return gender.toString();
            case WEIGHT:
                return String.valueOf(weight);

        }
        return "";
    }

    public void clear() {
        weight = 0;
        gender = Gender.NONE;
        age = Age.NONE;

    }
}

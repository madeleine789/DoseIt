package com.android_camp.doseit;

public class Parameter {
    String gender;
    double weight;
    double height;
    String age;

    public Parameter() {
        this.setGender("value");
        this.setAge("value");
        this.setWeight(-1);
        this.setHeight(-1);
    }

    public void setGender(String g) {
        gender = g;
    }

    public void setWeight(double w) {
        weight = w;
    }

    public void setHeight (double h) {
        height = h;
    }

    public void setAge(String a) {
        age = a;
    }

}

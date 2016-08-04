package com.android_camp.doseit;

/**
 * Created by demouser on 8/4/16.
 */

public class Medicine
{
    String wariningMessage;
    double concentration;
    double dose, kidDose;
    String name;

    public void setWariningMessage(String message) {
        wariningMessage = message;
    }

    public void setConcentration(double con) {
        concentration = con;
    }

    public void setDose (double dose) {
      this.dose = dose;
    }

    public void setKidDose(double kidDose) {
        this.kidDose = kidDose;
    }

    public void setName(String name) {
        this.name = name;
    }
}

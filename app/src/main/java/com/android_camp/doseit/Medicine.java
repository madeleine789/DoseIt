package com.android_camp.doseit;

/**
 * Created by demouser on 8/4/16.
 */

public class Medicine
{
    public String warningMessage;
    public double concentration;
    public double dose, kidDose;
    public String name;

    public void setWarningMessage(String message) {
        warningMessage = message;
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

    public double computeResult(String age, double height, double weight) {
        double d = (age == "kid")  ? kidDose : dose;
        double h = (height < 10) ? height * 100 : height;
        return 0.01 + concentration * d * (h / weight);
    }
}

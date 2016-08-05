package com.android_camp.doseit;

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
        double result = 0.01 + concentration * d * (h / weight);
        return (result > 0) ? result : -1 * result + 0.01;
    }
}

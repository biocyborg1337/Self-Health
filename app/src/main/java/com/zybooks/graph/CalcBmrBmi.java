package com.zybooks.graph;

public class CalcBmrBmi {
    private static final Integer BMICONST = 703;
    private static final Double mBMRCONST = 66.47;
    private static final Double wBMRCONST = 655.1;

    public Double bmiC(Double weight, Integer height) {
        return Math.ceil(BMICONST * weight / (height * height));
    }

    public Double wbmrC(Double weight, Integer height, Integer age) {
        return Math.ceil(wBMRCONST + (4.35 * weight) + (4.7 * height) - (4.7 * age));
    }

    public Double mbmrC(Double weight, Integer height, Integer age) {
        return Math.ceil(mBMRCONST + (6.24 * weight) + (12.7 * height) - (6.755 * age));
    }

}

package com.example.lj1_bd_2sem.simple_logic_exercises;

import java.math.BigDecimal;

public class Ex5 {
    private static double p1, e1, e2, x, sub, exf, api;

    public Ex5 (double p1, double e1, double e2, double x, double sub, double exf, double api){
        this.p1 = p1;
        this.e1 = e1;
        this.e2 = e2;
        this.x = x;
        this.sub = sub;
        this.exf = exf;
        this.api = api;
    }

    public static double checkAverage(){
        double pattern = p1*0.5+e1*0.2+e2*0.3+x;
        double final_formula = Math.max(((pattern+sub*0.15)*0.5)+(Math.max(pattern+(sub*0.15)-5.9, 0)/(pattern+(sub*0.15)-5.9))*api*0.5, exf);
        return final_formula;
    }


}

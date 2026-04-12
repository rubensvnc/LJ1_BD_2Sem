package com.example.lj1_bd_2sem.simple_logic_exercises;

import java.math.BigDecimal;

public class Ex4 {
    private static int jan_expenses = 15000;
    private static int feb_expenses = 23000;
    private static int mar_expenses = 17000;

    private static BigDecimal checkTotalExpenses(){
        return BigDecimal.valueOf(jan_expenses+feb_expenses+mar_expenses);

    }

    private static BigDecimal checkQuaterlyAverage(){
        return BigDecimal.valueOf((jan_expenses+feb_expenses+mar_expenses)/3);
    }

    public static void printTotalExpensesAverage() {
        BigDecimal expenses = checkTotalExpenses();
        BigDecimal avg = checkQuaterlyAverage();

        System.out.println("Total expenses: "+expenses);
        System.out.println("Quarterly average: "+avg);
    }

}

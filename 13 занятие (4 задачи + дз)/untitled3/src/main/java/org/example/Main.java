package org.example;

import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        new FinanceRecord(LocalDate.now(), "Расход","Продукты", 2000, 1);
    }
}
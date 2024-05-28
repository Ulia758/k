package org.example;

import java.time.LocalDate;

public class FinanceRecord {
    private LocalDate dateOfOperation;
    private String typeOfOperation;
    private String categoryOfOperation;
    private double sumOfOperation;
    private long chatId;

    public FinanceRecord(LocalDate dateOfOperation, String typeOfOperation, String categoryOfOperation, double sumOfOperation, long chatId) {
        this.dateOfOperation = dateOfOperation;
        this.typeOfOperation = typeOfOperation;
        this.categoryOfOperation = categoryOfOperation;
        this.sumOfOperation = sumOfOperation;
        this.chatId = chatId;
    }

    public LocalDate getDateOfOperation() {
        return dateOfOperation;
    }

    public void setDateOfOperation(LocalDate dateOfOperation) {
        this.dateOfOperation = dateOfOperation;
    }

    public String getTypeOfOperation() {
        return typeOfOperation;
    }

    public void setTypeOfOperation(String typeOfOperation) {
        this.typeOfOperation = typeOfOperation;
    }

    public String getCategoryOfOperation() {
        return categoryOfOperation;
    }

    public void setCategoryOfOperation(String categoryOfOperation) {
        this.categoryOfOperation = categoryOfOperation;
    }

    public double getSumOfOperation() {
        return sumOfOperation;
    }

    public void setSumOfOperation(double sumOfOperation) {
        this.sumOfOperation = sumOfOperation;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}

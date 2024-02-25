package com.example.analytics.exception;

public class BudgetNotEnoughException extends RuntimeException{
    public static final String BUDGET_NOT_ENOUGH = "profile budget is not enough for this analysis";

    public BudgetNotEnoughException() {
        super(BUDGET_NOT_ENOUGH);
    }
}

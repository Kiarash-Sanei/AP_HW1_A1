package model;

import java.util.ArrayList;

public class Loan {
    private static final ArrayList<Loan> allLoans = new ArrayList<>();
    private final Customer customer;
    private final int duration;
    private int remainingPayments;
    private final int interest;
    private final int amount;
    private static ArrayList<Loan> shouldBeRemoved = new ArrayList<>();

    public Loan(Customer customer, int duration, int interest, int amount) {
        this.customer = customer;
        this.duration = duration;
        this.interest = interest;
        this.amount = amount;
        this.remainingPayments = duration;
        allLoans.add(this);
    }

    public static void passMonth() {
        for (Loan loan : allLoans) {
            loan.passMonthEach();
        }
        for (Loan loan : shouldBeRemoved) {
            allLoans.remove(loan);
        }
        shouldBeRemoved = new ArrayList<>();
    }

    private double getPaymentAmount() {
        return this.amount * (1 + (double) interest / 100) / this.duration;
    }

    private void passMonthEach() {
        if (!this.customer.canPayLoan(getPaymentAmount())) {
            this.customer.addNegativeScore();
        } else {
            this.customer.payLoan(getPaymentAmount());
            this.remainingPayments--;
        }
        if (this.remainingPayments == 0) {
            shouldBeRemoved.add(this);
        }
    }
}

package model;

import java.util.ArrayList;

public class Account {
    private static final ArrayList<Account> allAccounts = new ArrayList<>();
    private final Bank bank;
    private final int id;
    private final int money;
    private int remainingDuration;
    private final int interest;
    private final Customer customer;
    private static ArrayList<Account> shouldBeRemoved = new ArrayList<>();

    public Account(Bank bank, Customer customer, int id, int money, int duration, int interest) {
        this.bank = bank;
        this.customer = customer;
        this.id = id;
        this.money = money;
        this.remainingDuration = duration;
        this.interest = interest;
        allAccounts.add(this);
    }

    public static void passMonth() {
        for (Account account : allAccounts) {
            account.passMonthEach();
        }
        for (Account account : shouldBeRemoved) {
            allAccounts.remove(account);
        }
        shouldBeRemoved = new ArrayList<>();
    }

    public static void deleteAccount(Account account) {
        shouldBeRemoved.add(account);
        account.customer.setMoneyInSafe(account.getAmountOfMoneyForLeaving());
    }

    public int getId() {
        return this.id;
    }

    public double getAmountOfMoneyForLeaving() {
        if (this.remainingDuration == 0)
            return this.money * (1 + (double) this.interest / 100);
        else {
            for (Account account : shouldBeRemoved) {
                allAccounts.remove(account);
            }
            shouldBeRemoved = new ArrayList<>();
            return this.money;
        }
    }

    public Bank getBank() {
        return bank;
    }

    private void passMonthEach() {
        this.remainingDuration--;
        if (this.remainingDuration == 0)
            this.customer.leaveAccount(this.id);
    }
}

package model;

import java.util.ArrayList;

public class Customer {
    private static final ArrayList<Customer> allCustomers = new ArrayList<>();
    private String name;
    private double moneyInSafe;
    private ArrayList<Account> allActiveAccounts;
    private int totalNumberOfAccountsCreated;
    private int negativeScore;

    public Customer(String name, double moneyInSafe) {
        for (Customer customer : allCustomers)
            if (customer.getName().equals(name))
                return;
        this.name = name;
        this.moneyInSafe = moneyInSafe;
        this.allActiveAccounts = new ArrayList<>();
        allCustomers.add(this);
    }

    public static Customer getCustomerByName(String name) {
        for (Customer customer : allCustomers)
            if (customer.getName().equals(name))
                return customer;
        return null;
    }

    public String getName() {
        return name;
    }

    public void createNewAccount(Bank bank, int money, int duration, int interest) {
        if (this.getMoneyInSafe() < money) {
            System.out.println("Boro baba pool nadari!");
            return;
        }
        this.totalNumberOfAccountsCreated++;
        allActiveAccounts.add(new Account(bank, this, this.totalNumberOfAccountsCreated, money, duration, interest));
        this.setMoneyInSafe(-money);
    }

    public void leaveAccount(int accountId) {
        Account account = this.getAccountWithId(accountId);
        if (account == null) {
            System.out.println("Chizi zadi?!");
            return;
        }
        this.allActiveAccounts.remove(account);
        Account.deleteAccount(account);
    }

    public boolean canPayLoan(double amount) {
        return this.getMoneyInSafe() >= amount;
    }

    public double getMoneyInSafe() {
        return moneyInSafe;
    }

    public void setMoneyInSafe(double moneyInSafe) {
        this.moneyInSafe += moneyInSafe;
    }

    public void getLoan(int duration, int interest, int money) {
        if (!canGetLoan()) {
            System.out.println("To yeki kheyli vazet bade!");
            return;
        }
        this.setMoneyInSafe(money);
        new Loan(this, duration, interest, money);
    }

    public void payLoan(double amount) {
        setMoneyInSafe(-amount);
    }

    public boolean canGetLoan() {
        return !(this.getNegativeScore() >= 5);
    }

    public int getNegativeScore() {
        return negativeScore;
    }

    public void addNegativeScore() {
        this.negativeScore++;
    }

    public boolean hasActiveAccountInBank(Bank bank) {
        for (Account account : allActiveAccounts)
            if (account.getBank() == bank)
                return true;
        return false;
    }

    private Account getAccountWithId(int id) {
        for (Account account : this.allActiveAccounts)
            if (account.getId() == id)
                return account;
        return null;
    }
}

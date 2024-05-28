package model;

import java.util.ArrayList;

public class Bank {
    private static final ArrayList<Bank> allBanks = new ArrayList<>();
    private final String name;

    public Bank(String name) {
        this.name = name;
        allBanks.add(this);
    }

    public static Bank getBankWithName(String name) {
        for (Bank bank : allBanks)
            if (bank.getName().equals(name))
                return bank;
        return null;
    }

    public static boolean isThereBankWithName(String name) {
        for (Bank bank : allBanks)
            if (bank.getName().equals(name))
                return true;
        return false;
    }

    public static int getAccountInterestFromName(String type) {
        switch (type) {
            case ("KOOTAH"):
                return 10;
            case ("BOLAN"):
                return 30;
            case ("VIZHE"):
                return 50;
            default:
                return 0;
        }
    }

    public String getName() {
        return this.name;
    }
}

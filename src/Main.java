import model.Account;
import model.Bank;
import model.Customer;
import model.Loan;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String addingCostumer = "Add a customer with name (?<CustomerName>.+) and (?<Money>\\d+) unit initial money\\.";
    private static final String addingBank = "Create bank (?<BankName>.+)\\.";
    private static final String addingAccount = "Create a (?<AccountType>KOOTAH|BOLAN|VIZHE) account for (?<CustomerName>.+) in (?<BankName>.+), with duration (?<Duration>\\d+) and initial deposit of (?<InitialDeposit>.+)\\.";
    private static final String withdrawing = "Give (?<CustomerName>.+)'s money out of his account number (?<AccountId>\\d+)\\.";
    private static final String gettingLoan = "Pay a (?<Amount>\\d+) unit loan with %(?<Interest>\\d+) interest and (?<Duration>6|12) payments from (?<BankName>.+) to (?<CustomerName>.+)\\.";
    private static final String passingTime = "Pass time by (?<Amount>\\d+) unit\\.";
    private static final String safeMoneyReport = "Print (?<CustomerName>.+)'s GAVSANDOOGH money\\.";
    private static final String negativeScoreReport = "Print (?<CustomerName>.+)'s NOMRE MANFI count\\.";
    private static final String hasAccountInBankReport = "Does (?<CustomerName>.+) have active account in (?<BankName>.+)\\?";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String line = "";
        while (!line.equals("Base dige, berid khonehatoon.")) {
            line = input.nextLine();
            Matcher temporary = getMatcher(line, addingCostumer);
            if (temporary.matches()) {
                String name = temporary.group("CustomerName");
                int moneyInSafe = Integer.parseInt(temporary.group("Money"));
                new Customer(name, moneyInSafe);
                continue;
            }
            temporary = getMatcher(line, addingBank);
            if (temporary.matches()) {
                String name = temporary.group("BankName");
                new Bank(name);
                continue;
            }
            temporary = getMatcher(line, addingAccount);
            if (temporary.matches()) {
                String bankName = temporary.group("BankName");
                if (!Bank.isThereBankWithName(bankName)) {
                    System.out.println("In dige banke koodoom keshvarie?");
                    continue;
                }
                Bank bank = Bank.getBankWithName(bankName);
                String customerName = temporary.group("CustomerName");
                Customer customer = Customer.getCustomerByName(customerName);
                int money = Integer.parseInt(temporary.group("InitialDeposit"));
                int duration = Integer.parseInt(temporary.group("Duration"));
                int interest = Bank.getAccountInterestFromName(temporary.group("AccountType"));
                customer.createNewAccount(bank, money, duration, interest);
                continue;
            }
            temporary = getMatcher(line, withdrawing);
            if (temporary.matches()) {
                String customerName = temporary.group("CustomerName");
                Customer customer = Customer.getCustomerByName(customerName);
                int accountId = Integer.parseInt(temporary.group("AccountId"));
                customer.leaveAccount(accountId);
                continue;
            }
            temporary = getMatcher(line, gettingLoan);
            if (temporary.matches()) {
                String bankName = temporary.group("BankName");
                if (!Bank.isThereBankWithName(bankName)) {
                    System.out.println("Gerefti maro nesfe shabi?");
                    continue;
                }
                String customerName = temporary.group("CustomerName");
                Customer customer = Customer.getCustomerByName(customerName);
                int duration = Integer.parseInt(temporary.group("Duration"));
                int interest = Integer.parseInt(temporary.group("Interest"));
                int amount = Integer.parseInt(temporary.group("Amount"));
                customer.getLoan(duration, interest, amount);
                continue;
            }
            temporary = getMatcher(line, passingTime);
            if (temporary.matches()) {
                int unit = Integer.parseInt(temporary.group("Amount"));
                for (int i = 0; i < unit; i++)
                    passMonth();
                continue;
            }
            temporary = getMatcher(line, safeMoneyReport);
            if (temporary.matches()) {
                System.out.println((int) Customer.getCustomerByName(temporary.group("CustomerName")).getMoneyInSafe());
                continue;
            }
            temporary = getMatcher(line, negativeScoreReport);
            if (temporary.matches()) {
                System.out.println(Customer.getCustomerByName(temporary.group("CustomerName")).getNegativeScore());
                continue;
            }
            temporary = getMatcher(line, hasAccountInBankReport);
            if (temporary.matches()) {
                if (Customer.getCustomerByName(temporary.group("CustomerName")).hasActiveAccountInBank(Bank.getBankWithName(temporary.group("BankName"))))
                    System.out.println("yes");
                else
                    System.out.println("no");
            }
        }
    }

    private static void passMonth() {
        Loan.passMonth();
        Account.passMonth();
    }

    private static Matcher getMatcher(String input, String regex) {
        return Pattern.compile(regex).matcher(input);
    }
}
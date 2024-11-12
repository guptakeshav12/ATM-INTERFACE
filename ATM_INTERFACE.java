// ATM_SYSTEM.JAVA

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class to represent the bank account
class BankAccount {
    private double balance;
    private String pin;
    private List<String> miniStatement;

    // Constructor to initialize account balance and PIN
    public BankAccount(double initialBalance, String initialPin) {
        this.balance = initialBalance > 0 ? initialBalance : 0;
        this.pin = initialPin;
        this.miniStatement = new ArrayList<>();
        addTransaction("Account opened with balance: $" + this.balance);
    }

    // Method to deposit an amount
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited: $" + amount);
            addTransaction("Deposited: $" + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Method to withdraw an amount
    public void withdraw(double amount) {
        if (amount > 0) {
            if (amount <= balance) {
                balance -= amount;
                System.out.println("Successfully withdrew: $" + amount);
                addTransaction("Withdrew: $" + amount);
            } else {
                System.out.println("Insufficient balance for this withdrawal.");
            }
        } else {
            System.out.println("Withdrawal amount must be positive.");
        }
    }

    // Method to check the balance
    public double checkBalance() {
        return balance;
    }

    // Method to change the PIN
    public boolean changePin(String oldPin, String newPin) {
        if (pin.equals(oldPin)) {
            pin = newPin;
            System.out.println("PIN successfully changed.");
            return true;
        } else {
            System.out.println("Incorrect old PIN.");
            return false;
        }
    }

    // Method to get mini statement
    public void getMiniStatement() {
        System.out.println("Mini Statement:");
        for (String transaction : miniStatement) {
            System.out.println(transaction);
        }
    }

    // Add a transaction to the mini statement
    private void addTransaction(String transaction) {
        if (miniStatement.size() >= 5) {
            miniStatement.remove(0); // Keep only the last 5 transactions
        }
        miniStatement.add(transaction);
    }

    // Method to verify PIN
    public boolean verifyPin(String enteredPin) {
        return pin.equals(enteredPin);
    }
}

// Class to represent the ATM machine
class ATM {
    private BankAccount bankAccount;
    private Scanner scanner;

    // Constructor to link the ATM with a bank account
    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        this.scanner = new Scanner(System.in);
    }

    // Method to show ATM options
    public void showMenu() {
        System.out.println("Welcome to the ATM!");

        if (authenticateUser()) {
            int option;
            do {
                System.out.println("\nATM Menu:");
                System.out.println("1. Balance Enquiry");
                System.out.println("2. Cash Deposit");
                System.out.println("3. Cash Withdrawal");
                System.out.println("4. Mini Statement");
                System.out.println("5. PIN Change");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        // Balance Enquiry
                        System.out.println("Your current balance is: $" + bankAccount.checkBalance());
                        break;
                    case 2:
                        // Cash Deposit
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        bankAccount.deposit(depositAmount);
                        break;
                    case 3:
                        // Cash Withdrawal
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        bankAccount.withdraw(withdrawAmount);
                        break;
                    case 4:
                        // Mini Statement
                        bankAccount.getMiniStatement();
                        break;
                    case 5:
                        // PIN Change
                        changePin();
                        break;
                    case 6:
                        // Exit
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } while (option != 6);
        } else {
            System.out.println("Authentication failed. Exiting ATM.");
        }
    }

    // Authenticate the user with a PIN
    private boolean authenticateUser() {
        System.out.print("Enter your PIN: ");
        String enteredPin = scanner.next();
        return bankAccount.verifyPin(enteredPin);
    }

    // Method to change the PIN
    private void changePin() {
        System.out.print("Enter your current PIN: ");
        String currentPin = scanner.next();
        System.out.print("Enter your new PIN: ");
        String newPin = scanner.next();
        bankAccount.changePin(currentPin, newPin);
    }
}

// Main class to run the ATM program
public class ATM_INTERFACE {
    public static void main(String[] args) {
        // Initialize bank account with a starting balance and a PIN
        BankAccount userAccount = new BankAccount(1000, "1234"); // Example initial balance of $1000 and PIN "1234"

        // Create an ATM instance connected to the user's account
        ATM atm = new ATM(userAccount);

        // Show ATM menu
        atm.showMenu();
    }
}


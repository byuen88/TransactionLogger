package ui;

import model.Logger;
import model.Date;
import model.Transaction;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.exceptions.InvalidDateException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/*
 * Modelled after the sample applications given
 *
 * Represents the user interface in which the Transaction Logger
 * application is used.
 */
public class TransactionLoggerUI {
    private static final String JSON_STORE = "./data/logger.json";
    private Logger log;
    private Scanner input;
    private double limit;
    private boolean isThereALimit;
    private boolean isSaved;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: runs the logger application
    public TransactionLoggerUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runLogger();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runLogger() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q") && !isSaved) {
                System.out.println("You have not saved yet. Do you wish to continue? (yes/no)");
                command = input.next();
                if (command.equals("yes")) {
                    keepGoing = false;
                }
            } else {
                if (command.equals("q")) {
                    keepGoing = false;
                } else {
                    processCommand(command);
                }
            }
        }
        System.out.println("Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "t":
                displayTransactionOptions();
                command = input.next();
                processTransactionOptions(command);
                break;
            case "a":
                displayAnalysisOptions();
                command = input.next();
                processAnalysisCommand(command);
                break;
            case "s":
                saveLog();
                break;
            case "l":
                loadLog();
                break;
            default:
                System.out.println("Selection not valid");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for transaction options
    private void processTransactionOptions(String command) {
        switch (command) {
            case "c":
                createLimit();
                break;
            case "v":
                viewLimit();
                break;
            case "a":
                addTransactionDateOptions();
                command = input.next();
                processTransactionDateOptions(command);
                break;
            case "r":
                removeTransaction();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes adding a transaction's date
    private void processTransactionDateOptions(String command) {
        switch (command) {
            case "t":
                addTransactionToToday();
                break;
            case "m":
                processAddTransaction();
                break;
            default:
                System.out.println("Selection not valid");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for summary options
    private void processAnalysisCommand(String command) {
        switch (command) {
            case "s":
                doShowTransactionsDay();
                break;
            case "d":
                doTotalAmountDay();
                break;
            case "m":
                doTotalAmountMonth();
                break;
            case "y":
                doTotalAmountYear();
                break;
            case "t":
                doTotalAmountType();
                break;
            case "q":
                break;
            default:
                System.out.println("Selection not valid");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes logger
    private void init() {
        log = new Logger();
        input = new Scanner(System.in);
        limit = 0.0;
        isThereALimit = false;
        isSaved = false;
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\tt   -> display transaction options");
        System.out.println("\ta   -> display analysis options");
        System.out.println("\ts   -> save logger to file");
        System.out.println("\tl   -> load logger from file");
        System.out.println("\tq   -> quit");
    }

    // EFFECTS: displays transaction options to user
    private void displayTransactionOptions() {
        System.out.println("\tc   -> create a monthly transaction limit");
        System.out.println("\tv   -> view your monthly transaction limit");
        System.out.println("\ta   -> add a transaction");
        System.out.println("\tr   -> remove a transaction");
    }

    // EFFECTS: displays summary options to user
    private void displayAnalysisOptions() {
        System.out.println("What would you like to know?");
        System.out.println("\ts   -> show all transaction on a given day");
        System.out.println("\td   -> total amount spent on a day");
        System.out.println("\tm   -> total amount spent on a month");
        System.out.println("\ty   -> total amount spent in a year");
        System.out.println("\tt   -> amount spent on each transaction type shown in numbers and in percentages");
        System.out.println("\tq   -> quit");
    }

    // EFFECTS: displays adding transaction options to user
    private void addTransactionDateOptions() {
        System.out.println("How would you like to enter the transaction's date");
        System.out.println("\tt   -> use today's date");
        System.out.println("\tm   -> manually enter a date");
    }

    // EFFECTS: displays transaction type options to user
    private void displayTransactionTypeOptions() {
        int count = 0;
        System.out.println("Here are possible Transaction Types:");
        for (Transaction.TransactionType tt : Transaction.TransactionType.values()) {
            count++;
            System.out.println("(" + count + ") " + tt);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets transaction limit
    private void createLimit() {
        System.out.println("Enter your monthly transaction limit");
        log.setLimit(input.nextDouble());
        isThereALimit = true;
    }

    // MODIFIES: this
    // EFFECTS: checks if a limit is set
    private void checkLimit() {
        if (limit != 0.0) {
            isThereALimit = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: views transaction limit
    private void viewLimit() {
        System.out.println("This is your monthly transaction limit: " + log.getLimit());
    }

    // MODIFIES: this
    // EFFECTS: processes adding a transaction to the logger using today's date
    private void addTransactionToToday() {
        LocalDate today = LocalDate.now();

        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        int year = today.getYear();
        input.nextLine();

        System.out.println("Name of Transaction");
        String name = input.nextLine();
        System.out.println("Amount of Transaction");
        double amount = input.nextDouble();
        addTransaction(month, day, year, name, amount);
    }

    // MODIFIES: this
    // EFFECTS: processes adding a transaction to the logger
    private void processAddTransaction() {
        System.out.println("Enter month in digits");
        int month = input.nextInt();
        System.out.println("Enter day in digits");
        int day = input.nextInt();
        System.out.println("Enter year in digits");
        int year = input.nextInt();
        input.nextLine();

        try {
            if (Date.isValidDate(month, day, year)) {
                System.out.println("Name of Transaction");
                String name = input.nextLine();
                System.out.println("Amount of Transaction");
                double amount = input.nextDouble();
                addTransaction(month, day, year, name, amount);
            }
        } catch (InvalidDateException e) {
            System.out.println("This is not a valid date");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds transaction to the log
    private void addTransaction(int month, int day, int year, String name, double amount) {
        limit = log.getLimit();
        checkLimit();
        if (amount >= 0.0) {
            if (isThereALimit && limit != 0.0 && (log.monthTransactionAmount(month, year) + amount) > limit) {
                System.out.println("Warning! This transaction will exceed your monthly transaction limit of $" + limit);
                System.out.println("Would you like to continue? (yes/no)");
                String response = input.next();

                if (response.equals("yes")) {
                    System.out.println("Type of Transaction");
                    displayTransactionTypeOptions();
                    Transaction.TransactionType tt = Transaction.mapTransactionType(input.next().toLowerCase());
                    log.getDate(month, day, year).addTransaction(new Transaction(name, amount, tt));
                }
            } else {
                System.out.println("Type of Transaction");
                displayTransactionTypeOptions();
                Transaction.TransactionType tt = Transaction.mapTransactionType(input.next().toLowerCase());
                log.getDate(month, day, year).addTransaction(new Transaction(name, amount, tt));
            }
        } else {
            System.out.println("Cannot add a negative transaction");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a transaction from the logger
    private void removeTransaction() {
        System.out.println("Enter month in digits");
        int month = input.nextInt();
        System.out.println("Enter day in digits");
        int day = input.nextInt();
        System.out.println("Enter year in digits");
        int year = input.nextInt();
        input.nextLine();
        System.out.println("Name of Transaction");
        String name = input.nextLine();

        try {
            if (Date.isValidDate(month, day, year)) {
                if (log.removeTransactionFromDay(month, day, year, name)) {
                    System.out.println("Transaction has been removed");
                } else {
                    System.out.println("This transaction does not exist!");
                }
            }
        } catch (InvalidDateException e) {
            System.out.println("This is not a valid date");
        }
    }

    // MODIFIES: this
    // EFFECTS: shows all transaction on a day
    private void doShowTransactionsDay() {
        System.out.println("Enter month in digits");
        int month = input.nextInt();
        System.out.println("Enter day in digits");
        int day = input.nextInt();
        System.out.println("Enter year in digits");
        int year = input.nextInt();

        List<Transaction> transactions = log.getTransactionsOfDay(month, day, year);

        try {
            if (Date.isValidDate(month, day, year)) {
                if (transactions.isEmpty()) {
                    System.out.println("There are no transactions on this date");
                } else {
                    for (Transaction t : transactions) {
                        System.out.println("Name: " + t.getTransactionName() + "\nAmount: " + t.getAmount() + "\n");
                    }
                }
            }
        } catch (InvalidDateException e) {
            System.out.println("This is not a valid date");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts total amount of a day
    private void doTotalAmountDay() {
        System.out.println("Enter month in digits");
        int month = input.nextInt();
        System.out.println("Enter day in digits");
        int day = input.nextInt();
        System.out.println("Enter year in digits");
        int year = input.nextInt();

        try {
            if (Date.isValidDate(month, day, year)) {
                System.out.println("Total: $" + log.getDate(month, day, year).amountOfTheDay());
            }
        } catch (InvalidDateException e) {
            System.out.println("This is not a valid date");
        }

    }

    // MODIFIES: this
    // EFFECTS: conducts total amount of a month
    private void doTotalAmountMonth() {
        limit = log.getLimit();
        System.out.println("Enter month in digits");
        int month = input.nextInt();
        System.out.println("Enter year in digits");
        int year = input.nextInt();

        if (month <= 12 && month >= 1) {
            checkLimit();
            if (log.monthTransactionAmount(month, year) > limit) {
                System.out.println("Warning! You have exceeded your transaction limit of $" + limit + " this month");
                System.out.println("Total: $" + log.monthTransactionAmount(month, year));
            } else {
                System.out.println("Total: $" + log.monthTransactionAmount(month, year));
            }
        } else {
            System.out.println("This is not a valid date");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts total amount of a year
    private void doTotalAmountYear() {
        System.out.println("Enter year in digits");
        int year = input.nextInt();

        System.out.println("Total: $" + log.totalTransactionAmount(year));
    }

    // MODIFIES: this
    // EFFECTS: conducts amount spent each type
    private void doTotalAmountType() {
        System.out.println("Enter year in digits");
        int year = input.nextInt();

        for (Transaction.TransactionType tt : Transaction.TransactionType.values()) {
            double value = log.totalSpecificTransactionTypePerYearPercentage(tt, year);
            DecimalFormat f = new DecimalFormat("##.0");
            System.out.println(tt + " " + "Total: $" + log.totalSpecificTransactionTypePerYear(tt, year));
            if (value == 0.0) {
                System.out.println(tt + " " + "Percentage:" + "0.0%");
                System.out.print("\n");
            } else {
                System.out.println(tt + " " + "Percentage:" + " " + f.format(value) + "%");
                System.out.print("\n");
            }
        }
    }

    // EFFECTS: saves the log to file
    private void saveLog() {
        try {
            jsonWriter.open();
            jsonWriter.write(log);
            jsonWriter.close();
            System.out.println("Saved to" + JSON_STORE);
            isSaved = true;
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads log from file
    private void loadLog() {
        try {
            log = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

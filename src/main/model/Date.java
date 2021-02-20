package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import ui.exceptions.InvalidDateException;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a date.
 */
public class Date implements Writable, Comparable<Date> {

    private int month;
    private int day;
    private int year;
    private List<Transaction> transactions;

    // MODIFIES: this
    // EFFECTS: constructs a Date
    public Date(int month, int day, int year) {
        this.year = year;
        this.month = month;
        this.day = day;
        transactions = new ArrayList<>();
    }

    // getters
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public java.util.Date getDateTime() {
        return new java.util.Date(year, month, day);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // EFFECTS: returns true if date is a valid date, throws InvalidDateException otherwise
    public static boolean isValidDate(int month, int day, int year) throws InvalidDateException {
        boolean result;

        if (isLeapYear(year)) {
            result = processLeapYear(month, day);
        } else {
            if (month == 2) {
                result = day <= 28 && day >= 1;
            } else {
                if (doesMonthHave31Days(month)) {
                    result = day <= 31 && day >= 1;
                } else {
                    result = month <= 12 && month >= 1 && day <= 30 && day >= 1;
                }
            }
        }

        if (result) {
            return result;
        } else {
            throw new InvalidDateException();
        }
    }

    // EFFECTS: returns true if year is a leap year
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    // EFFECTS: returns true if month has 31 days
    public static boolean doesMonthHave31Days(int month) {
        return (month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10)
                || (month == 12);
    }

    // EFFECTS: processes a leap year date
    public static boolean processLeapYear(int month, int day) {
        if (month == 2) {
            return day <= 29 && day >= 1;
        } else {
            if (doesMonthHave31Days(month)) {
                return day <= 31 && day >= 1;
            } else {
                return month <= 12 && month >= 1 && day <= 30 && day >= 1;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a Transaction t into this date
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    // MODIFIES: this
    // EFFECTS: removes a Transaction t from this date
    public void removeTransaction(Transaction t) {
        transactions.remove(t);
    }

    // MODIFIES: this
    // EFFECTS: computes the amount of money spent in this date
    public double amountOfTheDay() {
        double result = 0.0;

        for (Transaction t : transactions) {
            result += t.getAmount();
        }
        return result;
    }

    // MODIFIES: this
    // EFFECTS: computes the amount of money spent on a specific TransactionType on this date
    public double specificTypeAmountOfTheDay(Transaction.TransactionType tt) {
        double result = 0.0;

        for (Transaction t : transactions) {
            if (t.getType() == tt) {
                result += t.getAmount();
            }
        }
        return result;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("year", year);
        json.put("month", month);
        json.put("day", day);
        json.put("transactions", transactionsToJson());
        return json;
    }

    // EFFECTS: returns transactions in this date as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : transactions) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: compares the DateTime
    @Override
    public int compareTo(Date o) {
        return getDateTime().compareTo(o.getDateTime());
    }
}


package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

/*
 * Represents a transaction logger.
 */
public class Logger implements Writable {
    private List<Date> dates;
    public double limit;
    private boolean isThereALimit;

    // MODIFIES: this
    //EFFECTS:  Constructs a Logger
    public Logger() {
        dates = new ArrayList<>();
        limit = 0.0;
        isThereALimit = false;
    }

    // getters:
    public List<Date> getDates() {
        return dates;
    }

    public double getLimit() {
        return limit;
    }

    public boolean isThereALimit() {
        return isThereALimit;
    }

    // MODIFIES: this
    // EFFECTS: sets transaction limit
    public void setLimit(double l) {
        limit = l;
        isThereALimit = true;
    }

    // MODIFIES: this
    // EFFECTS: grabs a Date from the list of dates if the month and day and years match, otherwise,
    //          create a new date and add it to the list of dates.
    public Date getDate(int month, int day, int year) {
        Date newDate;

        for (Date d : dates) {
            if ((d.getMonth() == month) && (d.getDay() == day) && (d.getYear() == year)) {
                return d;
            }
        }
        newDate = new Date(month, day, year);
        dates.add(newDate);
        return newDate;
    }

    // EFFECTS: sorts the list of dates into chronological order
    public void sortDates() {
        Collections.sort(dates);
    }

    // MODIFIES: this
    // EFFECTS: adds date to this calendar
    public void addDate(Date date) {
        dates.add(date);
    }

    // MODIFIES: this
    // EFFECTS: returns all transactions in the specified date
    public List<Transaction> getTransactionsOfDay(int month, int day, int year) {
        List<Transaction> result = new LinkedList<>();

        for (Date d : dates) {
            if ((d.getMonth() == month) && (d.getDay() == day) && (d.getYear() == year)) {
                result = d.getTransactions();
            }
        }
        return result;
    }

    // MODIFIES: this
    // EFFECTS: removes a transaction specified by the date and name from a calendar and then returns true
    //          false otherwise
    public boolean removeTransactionFromDay(int month, int day, int year, String name) {
        Iterator<Transaction> iter = this.getTransactionsOfDay(month, day, year).iterator();

        while (iter.hasNext()) {
            Transaction t = iter.next();

            if (t.getTransactionName().equals(name)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: computes the total money spent in the specified year
    public double totalTransactionAmount(int year) {
        double result = 0.0;

        for (Date d : dates) {
            if (d.getYear() == year) {
                result += d.amountOfTheDay();
            }
        }
        return result;
    }

    // MODIFIES: this
    // EFFECTS: computes the total money spent in the specified month and year
    public double monthTransactionAmount(int month, int year) {
        double result = 0.0;

        for (Date d : dates) {
            if ((d.getMonth() == month) && (d.getYear() == year)) {
                result += d.amountOfTheDay();
            }
        }
        return result;
    }

    // MODIFIES: this
    // EFFECTS: computes the total money spent in the specified TransactionType and year
    public double totalSpecificTransactionTypePerYear(Transaction.TransactionType tt, int year) {
        double result = 0.0;

        for (Date d : dates) {
            if (d.getYear() == year) {
                result += d.specificTypeAmountOfTheDay(tt);
            }
        }
        return result;
    }

    // MODIFIES: this
    // EFFECTS: gets the percentage of spending for specified TransactionType and year
    public double totalSpecificTransactionTypePerYearPercentage(Transaction.TransactionType tt, int year) {
        double result = 0.0;

        for (Date d : dates) {
            if (d.getYear() == year) {
                result += d.specificTypeAmountOfTheDay(tt);
            }
        }
        if (this.totalTransactionAmount(year) == 0.0) {
            return result;
        } else {
            return (result / this.totalTransactionAmount(year)) * 100.0;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("limit", limit);
        json.put("dates", datesToJson());
        return json;
    }

    // EFFECTS: returns dates in this calendar as a JSON array
    private JSONArray datesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Date d : dates) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }
}

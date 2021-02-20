package model;

import org.json.JSONObject;
import persistence.Writable;

/*
 * Represents a transaction.
 */
public class Transaction implements Writable {
    private String name;
    private double amount;
    private TransactionType type;

    // MODIFIES: this
    // EFFECTS: constructs a Transaction
    public Transaction(String name, double amount, TransactionType type) {
        this.name = name;
        this.amount = amount;
        this.type = type;
    }

    // enum for TransactionType
    public enum TransactionType {
        GROCERY,
        ENTERTAINMENT,
        FOOD,
        SHOPPING,
        OTHER
    }

    // getters
    public String getTransactionName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    // EFFECTS: turns transaction type into a string
    public static String toString(TransactionType type) {

        if (type.equals(TransactionType.GROCERY)) {
            return "Grocery";
        } else if (type.equals(TransactionType.ENTERTAINMENT)) {
            return "Entertainment";
        } else if (type.equals(TransactionType.FOOD)) {
            return "Food";
        } else if (type.equals(TransactionType.SHOPPING)) {
            return "Shopping";
        } else {
            return "Other";
        }
    }

    // EFFECTS: maps the string type to its specific TransactionType
    public static TransactionType mapTransactionType(String type) {
        if (type.equals("1")) {
            return TransactionType.GROCERY;
        } else if (type.equals("2")) {
            return TransactionType.ENTERTAINMENT;
        } else {
            if (type.equals("3")) {
                return TransactionType.FOOD;
            } else {
                if (type.equals("4")) {
                    return TransactionType.SHOPPING;
                } else {
                    if (type.equals("5")) {
                        return TransactionType.OTHER;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("amount", amount);
        json.put("type", type);
        return json;
    }
}



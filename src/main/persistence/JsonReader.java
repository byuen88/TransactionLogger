package persistence;

import model.Logger;
import model.Date;
import model.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 * Modelled after the JsonSerializationDemo sample application
 *
 * Represents a reader that reads workroom from JSON data stored in file
 */
public class JsonReader {
    private String source;

    // MODIFIES: this
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads calendar from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Logger read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCalendar(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses calendar from JSON object and returns it
    private Logger parseCalendar(JSONObject jsonObject) {
        Logger cal = new Logger();
        double limit = jsonObject.getDouble("limit");
        cal.setLimit(limit);
        addDates(cal, jsonObject);
        return cal;
    }

    // MODIFIES: cal
    // EFFECTS: parses dates from JSON object and adds them to calendar
    private void addDates(Logger cal, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("dates");
        for (Object json : jsonArray) {
            JSONObject nextDate = (JSONObject) json;
            addDate(cal, nextDate);
        }
    }

    // MODIFIES: cal
    // EFFECTS: parses date from JSON object and adds it to calendar
    private void addDate(Logger cal, JSONObject jsonObject) {
        int month = jsonObject.getInt("month");
        int year = jsonObject.getInt("year");
        int day = jsonObject.getInt("day");
        Date date = new Date(month, day, year);
        cal.addDate(date);
        addTransactions(date, jsonObject);
    }

    // MODIFIES: date
    // EFFECTS: parses transactions from JSON object and adds them to the date
    private void addTransactions(Date date, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(date, nextTransaction);
        }
    }

    // MODIFIES: date
    // EFFECTS: parses transaction from JSON object and adds it to the date
    private void addTransaction(Date date, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double amount = jsonObject.getDouble("amount");
        Transaction.TransactionType type = Transaction.TransactionType.valueOf(jsonObject.getString("type"));
        Transaction transaction = new Transaction(name, amount, type);
        date.addTransaction(transaction);
    }
}

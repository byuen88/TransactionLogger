package persistence;

import model.Date;
import model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Modelled after the JsonSerializationDemo sample application
public class JsonTest {
    protected void checkDate(int month, int day, int year, Date date) {
        assertEquals(month, date.getMonth());
        assertEquals(day, date.getDay());
        assertEquals(year, date.getYear());
    }

    protected void checkTransaction(String name, double amount,
                                    Transaction.TransactionType type, Transaction transaction) {
        assertEquals(name, transaction.getTransactionName());
        assertEquals(amount, transaction.getAmount());
        assertEquals(type, transaction.getType());
    }
}

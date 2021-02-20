package persistence;

import model.Logger;
import model.Date;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Modelled after the JsonSerializationDemo sample application
public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInavlidFile() {
        try {
            Logger cal = new Logger();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCalendar() {
        try {
            Logger cal = new Logger();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCalendar1.json");
            writer.open();
            writer.write(cal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalendar1.json");
            cal = reader.read();
            assertEquals(0, cal.getDates().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCalendar() {
        try {
            Logger cal = new Logger();
            cal.setLimit(500.0);
            cal.getDate(6, 29, 2020).addTransaction(
                    new Transaction("food", 15.47, Transaction.TransactionType.FOOD));
            cal.getDate(4, 2, 2020).addTransaction(
                    new Transaction("flowers", 150.00, Transaction.TransactionType.SHOPPING));
            cal.getDate(4, 2, 2020).addTransaction(
                    new Transaction("cupcakes", 15.05, Transaction.TransactionType.FOOD));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCalendar");
            writer.open();
            writer.write(cal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCalendar");
            cal = reader.read();
            List<Date> dates = cal.getDates();
            List<Transaction> transaction1 = dates.get(0).getTransactions();
            List<Transaction> transaction2 = dates.get(1).getTransactions();
            assertEquals(500.0, cal.getLimit());
            assertEquals(2, cal.getDates().size());
            checkDate(6, 29, 2020, dates.get(0));
            checkTransaction("food", 15.47, Transaction.TransactionType.FOOD, transaction1.get(0));
            checkDate(4, 2, 2020, dates.get(1));
            checkTransaction("flowers", 150.00,
                    Transaction.TransactionType.SHOPPING, transaction2.get(0));
            checkTransaction("cupcakes", 15.05,
                    Transaction.TransactionType.FOOD, transaction2.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

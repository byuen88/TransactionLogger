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
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Logger cal = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCalendar() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCalendar.json");
        try {
            Logger cal = reader.read();
            assertEquals(0, cal.getDates().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCalendar() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCalendar.json");
        try {
            Logger cal = reader.read();
            List<Date> dates = cal.getDates();
            List<Transaction> transaction1 = dates.get(0).getTransactions();
            List<Transaction> transaction2 = dates.get(1).getTransactions();
            assertEquals(500.0, cal.getLimit());
            assertEquals(2, cal.getDates().size());
            checkDate(6, 29, 2020, dates.get(0));
            checkTransaction("food", 15.47, Transaction.TransactionType.FOOD, transaction1.get(0));
            checkDate(4, 24, 2020, dates.get(1));
            checkTransaction("aritzia jacket", 356.24,
                    Transaction.TransactionType.SHOPPING, transaction2.get(0));
            checkTransaction("birthday cake", 56.00,
                    Transaction.TransactionType.FOOD, transaction2.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

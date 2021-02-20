package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.exceptions.InvalidDateException;

import java.awt.*;
import java.text.DecimalFormat;

import static model.Transaction.TransactionType.*;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Unit tests for the classes in model.
 */
class LoggerTest {

    private Logger tData1;
    private Logger tData2;
    private Logger tLog1;
    private Logger tLog2;
    private Logger tLog3;
    private Date d1, d2, d3, d4, d5, d6, d7, d8, d9, dt, dt2, d10, d11, d12;
    private Transaction t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12;
    private Pie p1;
    private PieSlice ps1;
    private double delta;

    @BeforeEach
    public void runBefore() {
        tData1 = new Logger();
        tData2 = new Logger();
        tLog1 = new Logger();
        tLog2 = new Logger();
        tLog3 = new Logger();

        // dates for tests
        dt = tData1.getDate(10, 10, 2020);
        dt2 = tData1.getDate(12, 12, 2020);

        // dates in tData1
        d1 = tData1.getDate(6, 25, 2019);
        d2 = tData1.getDate(3, 14, 2019);
        d3 = tData1.getDate(6, 29, 2019);
        d4 = tData1.getDate(12, 14, 2019);
        d8 = tData1.getDate(11, 1, 2020);
        d10 = new Date(4, 20, 2020);

        // dates in tData2
        d5 = tData2.getDate(1, 15, 2020);
        d6 = tData2.getDate(2, 1, 2020);
        d7 = tData2.getDate(10, 31, 2020);
        d9 = tData2.getDate(1, 23, 2015);

        // dates in tCal2
        d11 = new Date(4, 20, 2020);
        tLog2.addDate(d11);

        // dates in tCal3
        d12 = tLog3.getDate(1, 1, 2020);
        t12 = new Transaction("foo", 5, OTHER);
        d12.addTransaction(t12);


        // transactions in tData1
        t1 = new Transaction("Shoes", 323, SHOPPING);
        t2 = new Transaction("Chicken Broth", 12.43, GROCERY);
        t3 = new Transaction("Cocoru", 45.36, FOOD);
        t4 = new Transaction("Dentist", 96.78, OTHER);
        t11 = new Transaction("Chips", 5.45, FOOD);

        // transactions in tData2
        t5 = new Transaction("Haircut", 28, OTHER);
        t6 = new Transaction("Netflix Subscription", 9.49, ENTERTAINMENT);
        t7 = new Transaction("Playing Cards", 5.67, ENTERTAINMENT);
        t8 = new Transaction("Movie", 13.78, ENTERTAINMENT);
        t9 = new Transaction("Steam Game", 15.99, ENTERTAINMENT);
        t10 = new Transaction("Shoe", 323.33, SHOPPING);

        d1.addTransaction(t1);
        d2.addTransaction(t2);
        d3.addTransaction(t3);
        d4.addTransaction(t4);
        d8.addTransaction(t11);

        d5.addTransaction(t5);
        d5.addTransaction(t6);
        d6.addTransaction(t7);
        d7.addTransaction(t8);
        d7.addTransaction(t9);
        d7.addTransaction(t10);

        p1 = new Pie(tData2, 2020);

        ps1 = new PieSlice(50, Color.RED);

        delta = 0.00001;
    }
    @Test
    public void testGetTransactionName() {
        assertEquals("Cocoru", t3.getTransactionName());
    }

    @Test
    public void testGetLimit() {
        tData1.setLimit(500.0);
        assertEquals(500.0, tData1.getLimit());
    }

    @Test
    public void testGetIsThereALimit() {
        tData1.setLimit(100.0);
        assertTrue(tData1.isThereALimit());
    }

    @Test
    public void testSortDates() {
        tData1.sortDates();
        assertEquals(tData1.getDates().get(0), d2);
    }

    @Test
    public void testGetTransactionsOfTheDay() {
        assertEquals(3, tData2.getTransactionsOfDay(10, 31, 2020).size());
        assertTrue(tData2.getTransactionsOfDay(10, 31, 2020).contains(t8));
        assertTrue(tData2.getTransactionsOfDay(10, 31, 2020).contains(t9));
        assertTrue(tData2.getTransactionsOfDay(10, 31, 2020).contains(t10));
        assertFalse(tData2.getTransactionsOfDay(10, 31, 2020).contains(t1));
        assertEquals(0, tData2.getTransactionsOfDay(1,1,1050).size());
    }

    @Test
    public void testGetTransactionsOfTheDayInvalidDate() {
        assertEquals(0, tData2.getTransactionsOfDay(10, 30, 2020).size());
        assertEquals(0, tData2.getTransactionsOfDay(9, 31, 2020).size());
        assertEquals(0, tData2.getTransactionsOfDay(10, 30, 2019).size());
        assertEquals(0, tData2.getTransactionsOfDay(8, 14, 2018).size());
        assertEquals(0, tData2.getTransactionsOfDay(1, 15, 1500).size());
    }

    @Test
    public void testRemoveTransactionFromDay() {
        assertTrue(tData2.removeTransactionFromDay(10, 31, 2020, "Shoe"));
        assertEquals(2, d7.getTransactions().size());
    }

    @Test
    public void testRemoveNonExistentTransactionFromDay() {
        assertFalse(tData2.removeTransactionFromDay(10, 31, 2020, "Random"));
    }

    @Test
    public void testGetDate() {
        assertEquals(d1, tData1.getDate(6, 25, 2019));
    }

    @Test
    public void testGetDateNewDate() {
        assertEquals(4, tData2.getDates().size());
        tData2.getDate(1, 15, 1500);
        assertEquals(5, tData2.getDates().size());
    }

    @Test
    public void testAddDate() {
        tData1.addDate(d10);
    }

    @Test
    public void testIsLeapYear() {
        assertTrue(Date.isLeapYear(2020));
        assertFalse(Date.isLeapYear(2021));
    }

    @Test
    public void testDoesMonthHave31Days() {
        assertTrue(Date.doesMonthHave31Days(3));
        assertTrue(Date.doesMonthHave31Days(5));
        assertTrue(Date.doesMonthHave31Days(7));
        assertTrue(Date.doesMonthHave31Days(8));
        assertTrue(Date.doesMonthHave31Days(10));
        assertTrue(Date.doesMonthHave31Days(12));
        assertFalse(Date.doesMonthHave31Days(2));
        assertFalse(Date.doesMonthHave31Days(4));
    }
    @Test
    public void testAddTransaction() {
        dt.addTransaction(new Transaction("Food", 5.00, FOOD));
        assertEquals(1, dt.getTransactions().size());
    }

    @Test
    public void testRemoveTransaction() {
        Transaction t = new Transaction("Grocery", 5.00, GROCERY);

        dt2.addTransaction(new Transaction("Food", 5.00, FOOD));
        dt2.addTransaction(t);
        dt2.removeTransaction(t);
        assertEquals(1, dt2.getTransactions().size());
    }

    @Test
    public void testAmountOfTheDay() {
        assertEquals(353.1, d7.amountOfTheDay(), delta);
        assertEquals(0.0, d9.amountOfTheDay(), delta);
    }

    @Test
    public void testSpecificTypeAmountOfTheDay() {
        assertEquals(29.77, d7.specificTypeAmountOfTheDay(ENTERTAINMENT), delta);
        assertEquals(0.0, d7.specificTypeAmountOfTheDay(FOOD), delta);
    }

    @Test
    public void testTotalTransactionAmount() {
        assertEquals(477.57, tData1.totalTransactionAmount(2019), delta);
        assertEquals(396.26, tData2.totalTransactionAmount(2020), delta);
    }

    @Test
    public void testMonthTransactionAmount() {
        assertEquals(12.43, tData1.monthTransactionAmount(3, 2019), delta);
        assertEquals(368.36, tData1.monthTransactionAmount(6, 2019), delta);
    }

    @Test
    public void testMonthTransactionAmountDifferentNoDate() {
        assertEquals(0.0, tData1.monthTransactionAmount(2, 2020));
        assertEquals(0.0, tData1.monthTransactionAmount(3, 2020));
        assertEquals(0.0, tData1.monthTransactionAmount(5, 2019));
    }

    @Test
    public void testTotalSpecificTransactionTypePerYear() {
        assertEquals(45.36, tData1.totalSpecificTransactionTypePerYear(FOOD, 2019), delta);
        assertEquals(44.93, tData2.totalSpecificTransactionTypePerYear(ENTERTAINMENT, 2020), delta);
    }

    @Test
    public void testTotalSpecificTransactionTypePerYearPercentage() {
        DecimalFormat f = new DecimalFormat("##.00");
        assertEquals(f.format(9.50),
                f.format(tData1.totalSpecificTransactionTypePerYearPercentage(FOOD, 2019)));
        assertEquals(0.0, tData1.totalSpecificTransactionTypePerYearPercentage(FOOD, 2000));
    }

    @Test
    public void testMapTransactionType() {
        assertEquals(GROCERY, Transaction.mapTransactionType("1"));
        assertEquals(ENTERTAINMENT, Transaction.mapTransactionType("2"));
        assertEquals(FOOD, Transaction.mapTransactionType("3"));
        assertEquals(SHOPPING, Transaction.mapTransactionType("4"));
        assertEquals(OTHER, Transaction.mapTransactionType("5"));
        assertNull(Transaction.mapTransactionType("random string"));
    }

    @Test
    public void testMapTransactionTypeDifferentString() {
        assertNull(Transaction.mapTransactionType("\0entertainment"));
        assertNull(Transaction.mapTransactionType("\0shopping"));
        assertNull(Transaction.mapTransactionType("\0food"));
        assertNull(Transaction.mapTransactionType("\0other"));
        assertNull(Transaction.mapTransactionType("\0grocery"));
    }

    @Test
    public void testTransactionToString() {
        assertEquals(Transaction.toString(GROCERY), "Grocery");
        assertEquals(Transaction.toString(ENTERTAINMENT), "Entertainment");
        assertEquals(Transaction.toString(FOOD), "Food");
        assertEquals(Transaction.toString(SHOPPING), "Shopping");
        assertEquals(Transaction.toString(OTHER), "Other");
    }

    @Test
    public void testLogToJsonNoDates() {
        assertEquals("{\"limit\":0,\"dates\":[]}", tLog1.toJson().toString());
    }

    @Test
    public void testLogToJsonWithDates() {
        assertEquals("{\"limit\":0,\"dates\":[{\"month\":4,\"year\":2020,\"transactions\":[],\"day\":20}]}",
                tLog2.toJson().toString());
    }

    @Test
    public void testDatesToJsonWithDatesWithTransactions() {
        assertEquals("{\"limit\":0,\"dates\":[{" +
                        "\"month\":1," +
                        "\"year\":2020," +
                        "\"transactions\":[{" +
                        "\"amount\":5," +
                        "\"name\":\"foo\"," +
                        "\"type\":\"OTHER\"}]," +
                        "\"day\":1" +
                        "}]}",
                tLog3.toJson().toString());

    }

    @Test
    public void testGetPieSlices() {
        assertEquals(p1.getSlices().size(), 5);
    }

    @Test
    public void testGetPieSliceValue() {
        assertEquals(ps1.getValue(), 50);
    }

    @Test
    public void testGetPieSliceColor() {
        assertEquals(ps1.getColor(), Color.RED);
    }

}
package model;

import org.junit.jupiter.api.Test;
import ui.exceptions.InvalidDateException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class IsValidDateTest {

    @Test
    public void testIsValidDateAllValid() {
        try {
            assertTrue(Date.isValidDate(12, 30, 2020));
            assertTrue(Date.isValidDate(12, 30, 2023));
            // expected
        } catch (InvalidDateException e) {
            fail("Exception thrown");
        }
    }

    @Test
    public void testIsValidDateInvalidCase1() {
        try {
            assertFalse(Date.isValidDate(13, 32, 2020));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateInvalidCase2() {
        try {
            assertFalse(Date.isValidDate(12, 32, 2020));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateInvalidCase3() {
        try {
            assertFalse(Date.isValidDate(12, -32, 2020));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateInvalidCase4() {
        try {
            assertFalse(Date.isValidDate(13, 30, 2020));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateInvalidCase5() {
        try {
            assertFalse(Date.isValidDate(-1, 30, 2020));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearAllValid() {
        try {
            assertTrue(Date.isValidDate(2, 29, 2020));
            assertTrue(Date.isValidDate(2, 29, 2024));
            assertTrue(Date.isValidDate(2, 29, 2000));

            assertTrue(Date.isValidDate(2, 25, 2023));
            assertTrue(Date.isValidDate(3, 25, 2023));
            // expected;
        } catch (InvalidDateException e) {
            fail("Exception thrown");
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase1() {
        try {
            assertFalse(Date.isValidDate(-1, 25, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase2() {
        try {
            assertFalse(Date.isValidDate(-1, -25, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase3() {
        try {
            assertFalse(Date.isValidDate(13, 24, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase4() {
        try {
            assertFalse(Date.isValidDate(13, 33, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase5() {
        try {
            assertFalse(Date.isValidDate(13, -33, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase6() {
        try {
            assertFalse(Date.isValidDate(-11, 33, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase7() {
        try {
            assertFalse(Date.isValidDate(1, 33, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase8() {
        try {
            assertFalse(Date.isValidDate(1, -25, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase9() {
        try {
            assertFalse(Date.isValidDate(2, 29, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase10() {
        try {
            assertFalse(Date.isValidDate(2, 32, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase11() {
        try {
            assertFalse(Date.isValidDate(2, -1, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase12() {
        try {
            assertFalse(Date.isValidDate(-2, 28, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase13() {
        try {
            assertFalse(Date.isValidDate(2, 29, 2019));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase14() {
        try {
            assertFalse(Date.isValidDate(2, 32, 2020));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDateLeapYearInvalidCase15() {
        try {
            assertFalse(Date.isValidDate(2, -1, 2020));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }



///
    @Test
    public void testIsValidDate31DaysAllValid() {
        try {
            assertTrue(Date.isValidDate(1, 31, 2020));
            assertTrue(Date.isValidDate(4, 30, 2020));
            assertTrue(Date.isValidDate(1, 31, 2023));
            assertTrue(Date.isValidDate(4, 30, 2023));
            // expected;
        } catch (InvalidDateException e) {
            fail("Exception thrown");
        }
    }

    @Test
    public void testIsValidDate31DaysInvalidCase1() {
        try {
            assertFalse(Date.isValidDate(4, 31, 2020));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDate31DaysInvalidCase2() {
        try {
            assertFalse(Date.isValidDate(4, -1, 2020));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDate31DaysInvalidCase3() {
        try {
            assertFalse(Date.isValidDate(1, -1, 2020));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDate31DaysInvalidCase4() {
        try {
            assertFalse(Date.isValidDate(1, 32, 2020));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDate31DaysInvalidCase5() {
        try {
            assertFalse(Date.isValidDate(4, 31, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDate31DaysInvalidCase6() {
        try {
            assertFalse(Date.isValidDate(4, -1, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDate31DaysInvalidCase7() {
        try {
            assertFalse(Date.isValidDate(1, -1, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

    @Test
    public void testIsValidDate31DaysInvalidCase8() {
        try {
            assertFalse(Date.isValidDate(1, 32, 2023));
            fail("Exception not thrown");
        } catch (InvalidDateException e) {
            // expected;
        }
    }

}

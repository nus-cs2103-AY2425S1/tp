package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtilDateTest;

public class DeadlineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        String invalidDeadline = "";
        assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDeadline));
    }

    @Test
    public void isValidDeadline() {
        // null deadline
        assertThrows(NullPointerException.class, () -> Deadline.isValidDeadline(null));

        // invalid strings
        assertFalse(Deadline.isValidDeadline(""));
        assertFalse(Deadline.isValidDeadline("hello world"));
        assertFalse(Deadline.isValidDeadline(" "));
        assertFalse(Deadline.isValidDeadline("\t"));
        assertFalse(Deadline.isValidDeadline("\n"));
        assertFalse(Deadline.isValidDeadline("abcd1234"));
        assertFalse(Deadline.isValidDeadline("12102025"));


        // invalid deadlines; see seedu/address/logic/parser/ParserUtilDateTest.java for test cases
        for (String deadline : ParserUtilDateTest.INVALID_DEADLINES_WRONG_NUMBERS) {
            assertFalse(Deadline.isValidDeadline(deadline));
        }
        for (String deadline : ParserUtilDateTest.INVALID_DEADLINES_WRONG_DELIMITER) {
            assertFalse(Deadline.isValidDeadline(deadline));
        }
        for (String deadline : ParserUtilDateTest.INVALID_DEADLINES_NUMBERS_OUTSIDE_RANGE) {
            System.out.println("Testing " + deadline);
            assertFalse(Deadline.isValidDeadline(deadline));
        }
        for (String deadline : ParserUtilDateTest.INVALID_DEADLINES_BORDER_VALUES) {
            assertFalse(Deadline.isValidDeadline(deadline));
        }

        // valid deadlines; see seedu/address/logic/parser/ParserUtilDateTest.java for test cases
        for (String deadline : ParserUtilDateTest.VALID_DEADLINES_EASY) {
            assertTrue(Deadline.isValidDeadline(deadline));
        }
        for (String deadline : ParserUtilDateTest.VALID_DEADLINES_DIFFERENT_DELIMITERS) {
            assertTrue(Deadline.isValidDeadline(deadline));
        }
        for (String deadline : ParserUtilDateTest.VALID_DEADLINES_SHORT_STRINGS) {
            assertTrue(Deadline.isValidDeadline(deadline));
        }
        for (String deadline : ParserUtilDateTest.VALID_DEADLINES_BORDER_VALUES) {
            assertTrue(Deadline.isValidDeadline(deadline));
        }
        for (String deadline : ParserUtilDateTest.VALID_DEADLINES_MIXED) {
            assertTrue(Deadline.isValidDeadline(deadline));
        }
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void equals() {
        Deadline deadline = new Deadline("01-01-2020");

        // same values -> returns true
        assertTrue(deadline.equals(new Deadline("01-01-2020")));

        // same object -> returns true
        assertTrue(deadline.equals(deadline));

        // null -> returns false
        assertFalse(deadline.equals(null));

        // different types -> returns false
        assertFalse(deadline.equals(5.0f));

        // different values -> returns false
        assertFalse(deadline.equals(new Deadline("02-01-2020")));
    }
}

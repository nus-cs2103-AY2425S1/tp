package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.util.SampleDataUtil;

public class MonthPaidTest {
    // TODO: add validation for invalid dates eg months past 12
    @Test
    public void isValidMonthPaid() {
        assertTrue(MonthPaid.isValidMonthPaid("2024-12"));
        assertFalse(MonthPaid.isValidMonthPaid("0000-00"));
    }

    @Test
    public void getSamplePersons_isValidMonthPaid_success() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        for (Person person : samplePersons) {
            for (MonthPaid monthPaid : person.getMonthsPaid()) {
                assertTrue(MonthPaid.isValidMonthPaid(monthPaid.monthPaidValue));
            }
        }
    }

    @Test
    public void equalsMethod() {
        MonthPaid monthPaid1 = new MonthPaid("2022-12");
        MonthPaid monthPaid2 = new MonthPaid("2022-12");
        MonthPaid monthPaid3 = new MonthPaid("2022-11");

        // same object -> returns true
        assertTrue(monthPaid1.equals(monthPaid1));

        // same values -> returns true
        assertTrue(monthPaid1.equals(monthPaid2));

        // different values -> returns false
        assertFalse(monthPaid1.equals(monthPaid3));

        // null -> returns false
        assertFalse(monthPaid1.equals(null));

        // different type -> returns false
        assertFalse(monthPaid1.equals("2022-12"));
    }
}

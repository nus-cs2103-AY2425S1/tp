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
}

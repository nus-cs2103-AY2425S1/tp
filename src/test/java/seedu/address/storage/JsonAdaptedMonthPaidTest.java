package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.MonthPaid;

public class JsonAdaptedMonthPaidTest {
    @Test
    public void toModelType_invalidMonthsPaid_throwsIllegalValueException() {
        JsonAdaptedMonthPaid j = new JsonAdaptedMonthPaid("123");
        String expectedMessage = MonthPaid.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, j::toModelType);
    }
    @Test
    public void constructor_withAnotherMonthPaid_success() {
        MonthPaid expectedMonthPaid = new MonthPaid("2024-01");
        JsonAdaptedMonthPaid jsonMonthPaid = new JsonAdaptedMonthPaid(expectedMonthPaid);
        assert(jsonMonthPaid.getMonthPaidValue().equals(expectedMonthPaid.monthPaidValue));
    }
}

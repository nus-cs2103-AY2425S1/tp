package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class ContractEndDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ContractEndDate.of(null));
    }

    @Test
    public void constructor_invalidCED_throwsIllegalArgumentException() {
        String invalidContractEndDate = "";
        assertThrows(IllegalArgumentException.class, () -> ContractEndDate.of(invalidContractEndDate));
    }

    @Test
    public void constructor_validEmptyCED() {
        ContractEndDate emptyContractEndDate = ContractEndDate.empty();
        assertEquals(emptyContractEndDate.getValue(), "");
        assertEquals(emptyContractEndDate.toString(), "");
    }

    @Test
    public void isValidCED() {
        // null contract end date
        assertThrows(NullPointerException.class, () -> ContractEndDate.isValidDate(null));

        // invalid contract end date
        assertFalse(ContractEndDate.isValidDate("")); // empty string
        assertFalse(ContractEndDate.isValidDate(" ")); // spaces only
        assertFalse(ContractEndDate.isValidDate("^")); // only non-alphanumeric characters
        assertFalse(ContractEndDate.isValidDate("peter*")); // contains non-alphanumeric characters
        assertFalse(ContractEndDate.isValidDate("01-01-2030")); // incorrect format
        assertFalse(ContractEndDate.isValidDate("2040-13-01")); // invalid month
        assertFalse(ContractEndDate.isValidDate("2021-02-29")); // invalid day

        // valid contract end date
        assertTrue(ContractEndDate.isValidDate("2020-02-29")); // valid day
    }

    @Test
    public void equals() {
        ContractEndDate contractEndDate = ContractEndDate.of("2020-02-28");
        ContractEndDate emptyContractEndDate = ContractEndDate.empty();

        // same values -> returns true
        assertTrue(contractEndDate.equals(ContractEndDate.of("2020-02-28")));

        // same object -> returns true
        assertTrue(contractEndDate.equals(contractEndDate));

        // null -> returns false
        assertFalse(contractEndDate.equals(null));

        // different types -> returns false
        assertFalse(contractEndDate.equals(5.0f));

        // different values -> returns false
        assertFalse(contractEndDate.equals(ContractEndDate.of("2020-02-20")));

        // same empty values -> return true
        assertTrue(emptyContractEndDate.equals(emptyContractEndDate));

        // same empty values -> return true
        assertTrue(emptyContractEndDate.equals(ContractEndDate.empty()));
    }
}

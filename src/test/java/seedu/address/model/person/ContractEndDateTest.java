package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ContractEndDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ContractEndDate.of(null));
    }

    @Test
    public void constructor_invalidContractEndDate_throwsIllegalArgumentException() {
        String invalidContractEndDate = "";
        assertThrows(IllegalArgumentException.class, () -> ContractEndDate.of(invalidContractEndDate));
    }

    @Test
    public void constructor_validEmptyContractEndDate() {
        ContractEndDate emptyContractEndDate = ContractEndDate.empty();
        assertEquals(emptyContractEndDate.getValue(), "");
        assertEquals(emptyContractEndDate.toString(), "");
    }

    @Test
    public void isValidContractEndDate() {
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

    @Test
    public void compareTo() {
        ContractEndDate contractEndDate = ContractEndDate.of("2020-02-28");
        ContractEndDate earlyContractEndDate = ContractEndDate.of("2000-01-01");
        ContractEndDate lateContractEndDate = ContractEndDate.of("2021-01-01");
        ContractEndDate emptyContractEndDate = ContractEndDate.empty();

        // same values -> returns 0
        assertEquals(0, contractEndDate.compareTo(ContractEndDate.of("2020-02-28")));

        // comapre to earlier value -> returns 1
        System.out.println(contractEndDate.compareTo(earlyContractEndDate) + "\n\n\n\n");
        assertEquals(1, contractEndDate.compareTo(earlyContractEndDate));

        // compare to later value -> returns -1
        assertEquals(-1, contractEndDate.compareTo(lateContractEndDate));

        // filled compare to empty -> returns -1
        assertEquals(-1, contractEndDate.compareTo(emptyContractEndDate));

        // empty compared to filled -> returns 1
        assertEquals(1, emptyContractEndDate.compareTo(contractEndDate));

        // same empty values -> return 0
        assertEquals(0, emptyContractEndDate.compareTo(ContractEndDate.empty()));
    }
}

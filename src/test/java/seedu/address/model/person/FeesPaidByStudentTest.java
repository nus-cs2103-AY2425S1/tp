package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FeesPaidByStudentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Expect a NullPointerException when passing a null balance
        assertThrows(NullPointerException.class, () -> new FeesPaidByStudent(null));
    }

    @Test
    public void constructor_validFees_success() {
        // Valid fees strings
        String validFees1 = "100";
        String validFees2 = "0";

        // Check construction does not throw any exceptions
        FeesPaidByStudent fees1 = new FeesPaidByStudent(validFees1);
        FeesPaidByStudent fees2 = new FeesPaidByStudent(validFees2);

        // Verify values
        assertEquals("100", fees1.value);
        assertEquals("0", fees2.value);
    }

    @Test
    public void isValidFees_validValues_returnsTrue() {
        // Valid positive integer values
        assertTrue(FeesPaidByStudent.isValidFees("100"));
        assertTrue(FeesPaidByStudent.isValidFees("1"));

        // Valid zero value
        assertTrue(FeesPaidByStudent.isValidFees("0"));
    }

    @Test
    public void isValidFees_invalidValues_returnsFalse() {
        // Non-numeric strings
        assertFalse(FeesPaidByStudent.isValidFees("abc"));

        // Negative numbers
        assertFalse(FeesPaidByStudent.isValidFees("-1"));

        // Decimal values
        assertFalse(FeesPaidByStudent.isValidFees("10.5"));

        // Empty string
        assertFalse(FeesPaidByStudent.isValidFees(""));

        // Spaces
        assertFalse(FeesPaidByStudent.isValidFees("   "));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        FeesPaidByStudent fees1 = new FeesPaidByStudent("100");
        FeesPaidByStudent fees2 = new FeesPaidByStudent("100");

        // Check equality based on value
        assertEquals(fees1, fees2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        FeesPaidByStudent fees1 = new FeesPaidByStudent("100");
        FeesPaidByStudent fees2 = new FeesPaidByStudent("200");

        // Check inequality based on value
        assertNotEquals(fees1, fees2);
    }

    @Test
    public void toString_correctFormat_returnsExpectedString() {
        FeesPaidByStudent fees = new FeesPaidByStudent("100");

        // Check that toString formats as expected
        assertEquals("Balance: 100", fees.toString());
    }

    @Test
    public void hashCode_sameValues_returnsSameHash() {
        FeesPaidByStudent fees1 = new FeesPaidByStudent("100");
        FeesPaidByStudent fees2 = new FeesPaidByStudent("100");

        // Check that hash codes are equal for objects with same value
        assertEquals(fees1.hashCode(), fees2.hashCode());
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHash() {
        FeesPaidByStudent fees1 = new FeesPaidByStudent("100");
        FeesPaidByStudent fees2 = new FeesPaidByStudent("200");

        // Check that hash codes are different for objects with different values
        assertNotEquals(fees1.hashCode(), fees2.hashCode());
    }
}

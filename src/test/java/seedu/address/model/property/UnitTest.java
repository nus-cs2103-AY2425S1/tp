package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_BEDOK;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UnitTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Unit(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidCode = "";
        assertThrows(IllegalArgumentException.class, () -> new Unit(invalidCode));
    }

    @Test
    public void isValidUnit() {
        // null name
        assertThrows(NullPointerException.class, () -> Unit.isValidUnit(null));

        // invalid name
        assertFalse(Unit.isValidUnit("")); // empty string
        assertFalse(Unit.isValidUnit(" ")); // spaces only
        assertFalse(Unit.isValidUnit("^")); // only non-alphanumeric characters
        assertFalse(Unit.isValidUnit("-00-00")); // contains non-alphanumeric characters
        assertFalse(Unit.isValidUnit("00 00")); // wrong delimiter
        assertFalse(Unit.isValidUnit("00&00")); // wrong character
        assertFalse(Unit.isValidUnit("0-00")); // few digits in level
        assertFalse(Unit.isValidUnit("00-0")); // few digits in unit
        assertFalse(Unit.isValidUnit("0-0")); // few digits in unit and level
        assertFalse(Unit.isValidUnit("149-1111")); // Too tall structure
        assertFalse(Unit.isValidUnit("148-111111")); // Too many units, limited to 6
        assertFalse(Unit.isValidUnit("009-11111119")); // Too much padding in level for single digit number
        assertFalse(Unit.isValidUnit("099-11111119")); // Too much padding in level for non-single digit number
        assertFalse(Unit.isValidUnit("09-009")); // Too much padding in unit for single digit number
        assertFalse(Unit.isValidUnit("09-099")); // Too much padding in unit for non-single digit number
        assertFalse(Unit.isValidUnit("9-099")); // No padding in level for single digit number
        assertFalse(Unit.isValidUnit("09-9")); // No padding in unit for single digit number

        // valid name
        assertTrue(Unit.isValidUnit("00-00")); // alphabets only
        assertTrue(Unit.isValidUnit("01-01"));
        assertTrue(Unit.isValidUnit("148-11111"));
    }

    @Test
    public void equals() {
        Unit unit = new Unit(VALID_UNIT_BEDOK);

        // same values -> returns true
        assertTrue(unit.equals(new Unit(VALID_UNIT_BEDOK)));

        // same object -> returns true
        assertTrue(unit.equals(unit));

        // null -> returns false
        assertFalse(unit.equals(null));

        // different types -> returns false
        assertFalse(unit.equals(5.0f));

        // different values -> returns false
        assertFalse(unit.equals(new Unit(VALID_UNIT_ADMIRALTY)));
    }
}

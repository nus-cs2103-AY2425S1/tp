package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_BEDOK;

import org.junit.jupiter.api.Test;

import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Unit;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePropertyCommandTest {

    @Test
    public void equals() {
        PostalCode postalCode1 = new PostalCode(VALID_POSTALCODE_ADMIRALTY);
        PostalCode postalCode2 = new PostalCode(VALID_POSTALCODE_BEDOK);
        Unit unit1 = new Unit(VALID_UNIT_ADMIRALTY);
        Unit unit2 = new Unit(VALID_UNIT_BEDOK);

        DeletePropertyCommand deleteFirstCommand = new DeletePropertyCommand(postalCode1, unit1);
        DeletePropertyCommand deleteSecondCommand = new DeletePropertyCommand(postalCode2, unit2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePropertyCommand deleteFirstCommandCopy = new DeletePropertyCommand(postalCode1, unit1);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different property -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        PostalCode postalCode = new PostalCode(VALID_POSTALCODE_ADMIRALTY);
        Unit unit = new Unit(VALID_UNIT_ADMIRALTY);
        DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(postalCode, unit);
        String expected = DeletePropertyCommand.class.getCanonicalName()
                + "{postalCode=" + postalCode + ", unitNumber=" + unit + "}";
        assertEquals(expected, deletePropertyCommand.toString());
    }
}

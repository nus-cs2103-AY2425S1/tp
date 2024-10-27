package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTFOUND_POSTALCODE_CLEMENTI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTFOUND_UNIT_CLEMENTI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertPropertyCommandFailure;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingBook;
import static seedu.address.testutil.TypicalProperty.getTypicalPropertyBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Unit;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePropertyCommandTest {

    private Model model = new ModelManager(new UserPrefs(), getTypicalPropertyBook(),
            getTypicalClientBook(), getTypicalMeetingBook());

    @Test
    public void execute_validPostalCodeAndUnitNumber_success() {
        PostalCode postalCode = new PostalCode(VALID_POSTALCODE_ADMIRALTY);
        Unit unit = new Unit(VALID_UNIT_ADMIRALTY);
        Property propertyToDelete = model.getFilteredPropertyList().stream()
                .filter(property -> property.getPostalCode().equals(postalCode)
                        && property.getUnit().equals(unit))
                .findFirst().orElseThrow(() -> new AssertionError(String.format("Property not found. ",
                        VALID_POSTALCODE_ADMIRALTY, VALID_UNIT_ADMIRALTY)));
        DeletePropertyCommand deletePropertyCommand =
                new DeletePropertyCommand(propertyToDelete.getPostalCode(), propertyToDelete.getUnit());

        String expectedMessage = String.format(DeletePropertyCommand.MESSAGE_DELETE_PROPERTY_SUCCESS,
                Messages.format(propertyToDelete));

        ModelManager expectedModel = new ModelManager(new UserPrefs(),
                getTypicalPropertyBook(), getTypicalClientBook(), getTypicalMeetingBook());

        expectedModel.deleteProperty(propertyToDelete);

        assertCommandSuccess(deletePropertyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_postalCodeNotFound_throwsCommandException() {
        PostalCode notFoundPostalCode = new PostalCode(VALID_NOTFOUND_POSTALCODE_CLEMENTI);
        Unit unit = new Unit(VALID_UNIT_ADMIRALTY);
        DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(notFoundPostalCode, unit);
        assertPropertyCommandFailure(deletePropertyCommand, model,
                String.format("Property not found. ", notFoundPostalCode, unit));
    }

    @Test
    public void execute_unitNumberNotFound_throwsCommandException() {
        PostalCode postalCode = new PostalCode(VALID_POSTALCODE_ADMIRALTY);
        Unit notFoundUnit = new Unit(VALID_NOTFOUND_UNIT_CLEMENTI);
        DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(postalCode, notFoundUnit);
        assertPropertyCommandFailure(deletePropertyCommand, model,
                String.format("Property not found. ", postalCode, notFoundUnit));
    }

    @Test
    public void execute_postalCodeAndUnitNumberNotFound_throwsCommandException() {
        PostalCode notFoundPostalCode = new PostalCode(VALID_NOTFOUND_POSTALCODE_CLEMENTI);
        Unit notFoundUnit = new Unit(VALID_NOTFOUND_UNIT_CLEMENTI);
        DeletePropertyCommand deletePropertyCommand = new DeletePropertyCommand(notFoundPostalCode, notFoundUnit);
        assertPropertyCommandFailure(deletePropertyCommand, model,
                String.format("Property not found. ", notFoundPostalCode, notFoundUnit));
    }

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

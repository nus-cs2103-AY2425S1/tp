package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_CONDO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_HDB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperty.getTypicalPropertyBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.Type;

public class FilterPropertyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalPropertyBook(),
            getTypicalClientBook());

    @Test
    public void execute_matchingName_filterSuccessful() {
        // Test case where a property's type matches the given type (valid match)
        String typePrefix = "CONDO";
        FilterPropertyCommand command = new FilterPropertyCommand(new Type(typePrefix));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getPropertyBook(),
                model.getClientBook());
        expectedModel.updateFilteredPropertyList(property ->
                property.getType().toString().matches("(?i)^" + typePrefix + ".*"));

        assertCommandSuccess(command,
                model, String.format("Listed filtered properties with properties of type: %s", typePrefix),
                expectedModel);
    }

    @Test
    public void execute_caseInsensitiveMatching_filterSuccessful() {
        // Test case where the filter should work regardless of case (case insensitive)
        String typePrefix = "condO";
        FilterPropertyCommand command = new FilterPropertyCommand(new Type(typePrefix));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getPropertyBook(),
                model.getClientBook());
        expectedModel.updateFilteredPropertyList(property ->
                property.getType().toString().matches("(?i)^" + typePrefix + ".*"));

        assertCommandSuccess(command,
                model, String.format("Listed filtered properties with properties of type: %s",
                        typePrefix.toUpperCase()), expectedModel);
    }


    @Test
    public void equals() {
        final FilterPropertyCommand standardCommand = new FilterPropertyCommand(new Type(VALID_TYPE_HDB));
        // same values -> returns true
        FilterPropertyCommand commandWithSameValues = new FilterPropertyCommand(new Type(VALID_TYPE_HDB));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different name -> returns false
        assertFalse(standardCommand.equals(new FilterPropertyCommand(new Type((VALID_TYPE_CONDO)))));
    }
}

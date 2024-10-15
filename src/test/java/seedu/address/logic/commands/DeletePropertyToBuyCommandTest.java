package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalIndexes.*;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeletePropertyToBuyCommand;
import seedu.address.logic.commands.DeletePropertyToBuyCommand.EditPersonPropertyDescriptor;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditPersonPropertyDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class DeletePropertyToBuyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonPropertyDescriptor descriptor = new EditPersonPropertyDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        DeletePropertyToBuyCommand editCommand = new DeletePropertyToBuyCommand(outOfBoundIndex,
                INDEX_FIRST_PROPERTY, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Deletes a property of a person in the filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeletePropertyToBuyCommand command = new DeletePropertyToBuyCommand(outOfBoundIndex,INDEX_FIRST_PROPERTY,
                new EditPersonPropertyDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeletePropertyToBuyCommand standardCommand = new DeletePropertyToBuyCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_PROPERTY, BUY_DESC_TOM);

        // same values -> returns true
        EditPersonPropertyDescriptor copyDescriptor = new EditPersonPropertyDescriptor(BUY_DESC_TOM);
        DeletePropertyToBuyCommand commandWithSameValues = new DeletePropertyToBuyCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_PROPERTY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeletePropertyToBuyCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PROPERTY, BUY_DESC_TOM)));

        // different descriptor -> returns false
    }

    @Test
    public void toStringMethod() {
        Index personIndex = INDEX_SECOND_PERSON;
        Index propertyIndex = INDEX_SECOND_PROPERTY;
        EditPersonPropertyDescriptor editPersonPropertyDescriptor = new EditPersonPropertyDescriptor();
        DeletePropertyToBuyCommand command = new DeletePropertyToBuyCommand(personIndex, propertyIndex, editPersonPropertyDescriptor);
        String expected = DeletePropertyToBuyCommand.class.getCanonicalName() + "{personIndex=" + personIndex + ", propertyIndex=" + propertyIndex + ", editPersonDescriptor="
                + editPersonPropertyDescriptor + "}";
        assertEquals(expected, command.toString());
    }
}

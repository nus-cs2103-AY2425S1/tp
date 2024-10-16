package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.BUY_DESC_JACK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROPERTY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROPERTY;
import static seedu.address.testutil.TypicalPersons.*;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
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
import seedu.address.testutil.TypicalPersons;

public class DeletePropertyToBuyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder(JACK).build();
        EditPersonPropertyDescriptor descriptor = new EditPersonPropertyDescriptorBuilder(editedPerson).build();
        Index jackIndex = TypicalPersons.getTypicalPersonIndex(JACK);
        DeletePropertyToBuyCommand command = new DeletePropertyToBuyCommand(jackIndex, INDEX_FIRST_PROPERTY,
                descriptor);

        String expectedMessage = String.format(DeletePropertyToBuyCommand.MESSAGE_PERSON_PROPERTY_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        int jackIndexInFilteredList = model.getFilteredPersonList().indexOf(JACK);
        expectedModel.setPerson(model.getFilteredPersonList().get(jackIndexInFilteredList), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

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
                INDEX_FIRST_PROPERTY, BUY_DESC_JACK);

        // same values -> returns true
        EditPersonPropertyDescriptor copyDescriptor = new EditPersonPropertyDescriptor(BUY_DESC_JACK);
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
        assertFalse(standardCommand.equals(new DeletePropertyToBuyCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PROPERTY, BUY_DESC_JACK)));

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

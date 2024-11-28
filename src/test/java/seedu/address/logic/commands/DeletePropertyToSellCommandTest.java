package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EVE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FOXY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROPERTY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROPERTY;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeletePropertyToSellCommand.EditPersonPropertyToSellDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonPropertyToSellDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class DeletePropertyToSellCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deletePropertyToSellFromPerson_success() {
        Person editedPerson = new PersonBuilder(ALICE).build();
        EditPersonPropertyToSellDescriptor descriptor =
                new EditPersonPropertyToSellDescriptorBuilder(editedPerson).build();
        Index index = TypicalPersons.getTypicalPersonIndex(ALICE);
        editedPerson.deleteSellProperty(index);
        DeletePropertyToSellCommand command = new DeletePropertyToSellCommand(index, INDEX_FIRST_PROPERTY, descriptor);

        String expectedMessage = String.format(DeletePropertyToSellCommand.MESSAGE_PERSON_PROPERTY_SUCCESS,
                Messages.formatProperties(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        int jackIndexInFilteredList = model.getFilteredPersonList().indexOf(ALICE);
        expectedModel.setPerson(model.getFilteredPersonList().get(jackIndexInFilteredList), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_propertyIndexOutOfBounds_failure() {
        Index index = TypicalPersons.getTypicalPersonIndex(ALICE);
        Person person = model.getFilteredPersonList().get(index.getZeroBased());
        Index invalidPropertyIndex = Index.fromOneBased(1000);

        DeletePropertyToSellCommand command = new DeletePropertyToSellCommand(index, invalidPropertyIndex,
                new EditPersonPropertyToSellDescriptorBuilder().withName(person.getName().fullName).build());

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonPropertyToSellDescriptor descriptor = new EditPersonPropertyToSellDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        DeletePropertyToSellCommand editCommand = new DeletePropertyToSellCommand(outOfBoundIndex,
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

        DeletePropertyToSellCommand command = new DeletePropertyToSellCommand(outOfBoundIndex, INDEX_FIRST_PROPERTY,
                new EditPersonPropertyToSellDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeletePropertyToSellCommand standardCommand = new DeletePropertyToSellCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_PROPERTY, DESC_EVE);

        // same values -> returns true
        EditPersonPropertyToSellDescriptor copyDescriptor = new EditPersonPropertyToSellDescriptor(DESC_EVE);
        DeletePropertyToSellCommand commandWithSameValues = new DeletePropertyToSellCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_PROPERTY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeletePropertyToSellCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PROPERTY,
                DESC_EVE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new DeletePropertyToSellCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PROPERTY,
                DESC_FOXY)));

    }

    @Test
    public void toStringMethod() {
        Index personIndex = INDEX_SECOND_PERSON;
        Index propertyIndex = INDEX_SECOND_PROPERTY;
        EditPersonPropertyToSellDescriptor editPersonPropertyDescriptor = new EditPersonPropertyToSellDescriptor();
        DeletePropertyToSellCommand command = new DeletePropertyToSellCommand(personIndex, propertyIndex,
                editPersonPropertyDescriptor);
        String expected = DeletePropertyToSellCommand.class.getCanonicalName() + "{personIndex=" + personIndex
                + ", propertyIndex=" + propertyIndex + ", editPersonDescriptor=" + editPersonPropertyDescriptor + "}";
        assertEquals(expected, command.toString());
    }
}

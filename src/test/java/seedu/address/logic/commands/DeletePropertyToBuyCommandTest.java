package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CLIF;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DAN;
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
import seedu.address.logic.commands.DeletePropertyToBuyCommand.EditPersonPropertyToBuyDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonPropertyToBuyDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class DeletePropertyToBuyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deletePropertyToBuyFromPerson_success() {
        Person editedPerson = new PersonBuilder(ALICE).build();
        EditPersonPropertyToBuyDescriptor descriptor =
                new EditPersonPropertyToBuyDescriptorBuilder(editedPerson).build();
        Index index = TypicalPersons.getTypicalPersonIndex(ALICE);
        editedPerson.deleteBuyProperty(index);
        DeletePropertyToBuyCommand command = new DeletePropertyToBuyCommand(index, INDEX_FIRST_PROPERTY, descriptor);

        String expectedMessage = String.format(DeletePropertyToBuyCommand.MESSAGE_PERSON_PROPERTY_SUCCESS,
                Messages.formatProperties(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        int jackIndexInFilteredList = model.getFilteredPersonList().indexOf(ALICE);
        expectedModel.setPerson(model.getFilteredPersonList().get(jackIndexInFilteredList), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_propertyIndexOutOfBounds_failure() {
        Index jackIndex = TypicalPersons.getTypicalPersonIndex(ALICE);
        Person jack = model.getFilteredPersonList().get(jackIndex.getZeroBased());
        Index invalidPropertyIndex = Index.fromOneBased(1000);

        DeletePropertyToBuyCommand command = new DeletePropertyToBuyCommand(jackIndex, invalidPropertyIndex,
                new EditPersonPropertyToBuyDescriptorBuilder().withName(jack.getName().fullName).build());

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonPropertyToBuyDescriptor descriptor = new EditPersonPropertyToBuyDescriptorBuilder()
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

        DeletePropertyToBuyCommand command = new DeletePropertyToBuyCommand(outOfBoundIndex, INDEX_FIRST_PROPERTY,
                new EditPersonPropertyToBuyDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeletePropertyToBuyCommand standardCommand = new DeletePropertyToBuyCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_PROPERTY, DESC_CLIF);

        // same values -> returns true
        EditPersonPropertyToBuyDescriptor copyDescriptor = new EditPersonPropertyToBuyDescriptor(DESC_CLIF);
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
        assertFalse(standardCommand.equals(new DeletePropertyToBuyCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PROPERTY,
                DESC_CLIF)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new DeletePropertyToBuyCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PROPERTY,
                DESC_DAN)));

    }

    @Test
    public void toStringMethod() {
        Index personIndex = INDEX_SECOND_PERSON;
        Index propertyIndex = INDEX_SECOND_PROPERTY;
        EditPersonPropertyToBuyDescriptor editPersonPropertyDescriptor = new EditPersonPropertyToBuyDescriptor();
        DeletePropertyToBuyCommand command = new DeletePropertyToBuyCommand(personIndex, propertyIndex,
                editPersonPropertyDescriptor);
        String expected = DeletePropertyToBuyCommand.class.getCanonicalName() + "{personIndex=" + personIndex
                + ", propertyIndex=" + propertyIndex + ", editPersonDescriptor=" + editPersonPropertyDescriptor + "}";
        assertEquals(expected, command.toString());
    }
}

package seedu.address.logic.commands.clientcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonWithName;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalNames;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.clientcommands.EditClientCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditClientCommandTest {

    private static final Name VALID_NAME = ALICE.getName();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Buyer editedPerson = new PersonBuilder().buildBuyer();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).withTags().build();
        EditClientCommand editClientCommand = new EditClientCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditClientCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Listings());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Person personToEdit = model.getPersonByName(VALID_NAME);

        PersonBuilder personInList = new PersonBuilder(personToEdit);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).buildBuyer();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson)
                .withTags(VALID_TAG_HUSBAND).build();
        EditClientCommand editClientCommand = new EditClientCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditClientCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Listings());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(editClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditClientCommand editClientCommand = new EditClientCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getPersonByName(VALID_NAME);

        String expectedMessage = String.format(EditClientCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Listings());

        assertCommandSuccess(editClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Random random = new Random();
        List<Name> typicalNames = getTypicalNames();
        int randomIndex = random.nextInt(typicalNames.size() - 1);

        showPersonWithName(model, typicalNames.get(randomIndex));

        Index validIndex = Index.fromZeroBased(model.getFilteredPersonList().size() - 1);

        Person personToEdit = model.getFilteredPersonList().get(validIndex.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withName(VALID_NAME_BOB).buildBuyer();
        EditClientCommand editClientCommand = new EditClientCommand(validIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditClientCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Listings());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(editClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getPersonByName(VALID_NAME);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditClientCommand editClientCommand = new EditClientCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editClientCommand, model, EditClientCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_editPersonToHaveSameAttributesAsDifferentPerson_failure() {
        showPersonWithName(model, ALICE.getName());

        // Try to edit ALICE to have the same attributes as CARL
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(CARL).build();
        EditClientCommand editClientCommand = new EditClientCommand(Index.fromZeroBased(0), descriptor);

        // Expect the duplicate person exception message
        assertCommandFailure(editClientCommand, model, EditClientCommand.MESSAGE_DUPLICATE_PERSON);
    }


    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromZeroBased(model.getFilteredPersonList().size() + 1);

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditClientCommand editClientCommand = new EditClientCommand(outOfBoundsIndex, descriptor);

        assertCommandFailure(editClientCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        Random random = new Random();
        List<Name> typicalNames = getTypicalNames();
        int randomIndex = random.nextInt(typicalNames.size() - 2);
        showPersonWithName(model, typicalNames.get(randomIndex));

        // Get the size of the filtered list (after applying filter) and create an index that's out of bounds for it
        int filteredListSize = model.getFilteredPersonList().size();
        Index outOfBoundsIndex = Index.fromZeroBased(filteredListSize);

        EditClientCommand editClientCommand = new EditClientCommand(outOfBoundsIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editClientCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final EditClientCommand standardCommand = new EditClientCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditClientCommand commandWithSameValues = new EditClientCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(standardCommand.equals(new EditClientCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditClientCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditClientCommand editClientCommand = new EditClientCommand(INDEX_FIRST_PERSON, editPersonDescriptor);
        String expected = EditClientCommand.class.getCanonicalName()
                + "{name=" + INDEX_FIRST_PERSON + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editClientCommand.toString());
    }

}

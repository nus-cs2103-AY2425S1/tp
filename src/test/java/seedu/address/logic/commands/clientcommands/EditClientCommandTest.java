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
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalNames;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

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
        EditClientCommand editClientCommand = new EditClientCommand(VALID_NAME, descriptor);

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
        EditClientCommand editClientCommand = new EditClientCommand(VALID_NAME, descriptor);

        String expectedMessage = String.format(EditClientCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Listings());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(editClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditClientCommand editClientCommand = new EditClientCommand(VALID_NAME, new EditPersonDescriptor());
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

        Person personToEdit = model.getPersonByName(typicalNames.get(randomIndex));
        Person editedPerson = new PersonBuilder(personToEdit).withName(VALID_NAME_BOB).buildBuyer();
        EditClientCommand editClientCommand = new EditClientCommand(personToEdit.getName(),
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
        EditClientCommand editClientCommand = new EditClientCommand(BENSON.getName(), descriptor);

        assertCommandFailure(editClientCommand, model, EditClientCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        Random random = new Random();
        List<Name> typicalNames = getTypicalNames();
        int randomIndex = random.nextInt(typicalNames.size() - 2);
        Person editedPerson = model.getPersonByName(typicalNames.get(randomIndex + 1));
        showPersonWithName(model, typicalNames.get(randomIndex));

        Person personToEdit = model.getPersonByName(typicalNames.get(randomIndex));

        EditClientCommand editClientCommand = new EditClientCommand(personToEdit.getName(),
                new EditPersonDescriptorBuilder(editedPerson).build());

        assertCommandFailure(editClientCommand, model, EditClientCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidNameUnfilteredList_failure() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditClientCommand editClientCommand = new EditClientCommand(HOON.getName(), descriptor);

        assertCommandFailure(editClientCommand, model, EditClientCommand.MESSAGE_INVALID_PERSON);
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

        EditClientCommand editClientCommand = new EditClientCommand(typicalNames.get(randomIndex + 1),
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editClientCommand, model, EditClientCommand.MESSAGE_INVALID_PERSON);
    }

    @Test
    public void equals() {
        final EditClientCommand standardCommand = new EditClientCommand(VALID_NAME, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditClientCommand commandWithSameValues = new EditClientCommand(VALID_NAME, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(standardCommand.equals(new EditClientCommand(BOB.getName(), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditClientCommand(ALICE.getName(), DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditClientCommand editClientCommand = new EditClientCommand(VALID_NAME, editPersonDescriptor);
        String expected = EditClientCommand.class.getCanonicalName()
                + "{name=" + VALID_NAME + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editClientCommand.toString());
    }

}

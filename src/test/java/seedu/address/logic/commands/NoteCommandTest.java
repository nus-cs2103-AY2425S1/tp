package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICATION_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_ONE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalNames.NAME_FIRST_PERSON;
import static seedu.address.testutil.TypicalNames.NAME_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.NoteCommand.NoteDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.NoteDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for NoteCommand.
 */
public class NoteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person originalPerson = model.getFilteredPersonList().get(0);
        Person editedPerson = new PersonBuilder(originalPerson)
                .withNote(VALID_APPOINTMENT_ONE, VALID_REMARK_ONE, VALID_MEDICATION_ONE)
                .build();

        NoteDescriptor descriptor = new NoteDescriptorBuilder(editedPerson).build();
        NoteCommand noteCommand = new NoteCommand(originalPerson.getName(), descriptor);

        String expectedMessage = String.format(NoteCommand.MESSAGE_EDIT_NOTE_SUCCESS,
                editedPerson.getNote().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(originalPerson, editedPerson);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Person originalPerson = model.getFilteredPersonList().get(0);
        Person editedPerson = new PersonBuilder(originalPerson)
                .withNote("01/01/2023 1200", null, null)
                .build();

        NoteDescriptor descriptor = new NoteDescriptorBuilder(editedPerson).build();
        NoteCommand noteCommand = new NoteCommand(originalPerson.getName(), descriptor);

        String expectedMessage = String.format(NoteCommand.MESSAGE_EDIT_NOTE_SUCCESS,
                editedPerson.getNote().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(originalPerson, editedPerson);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Person originalPerson = model.getFilteredPersonList().get(0);
        Person editedPerson = new PersonBuilder(originalPerson).build();

        NoteDescriptor descriptor = new NoteDescriptorBuilder(editedPerson).build();
        NoteCommand noteCommand = new NoteCommand(originalPerson.getName(), descriptor);

        String expectedMessage = String.format(NoteCommand.MESSAGE_EDIT_NOTE_SUCCESS,
                editedPerson.getNote().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(originalPerson, editedPerson);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withNote(VALID_APPOINTMENT_ONE, VALID_REMARK_ONE, VALID_MEDICATION_ONE)
                .build();
        NoteDescriptor descriptor = new NoteDescriptorBuilder(editedPerson).build();
        NoteCommand noteCommand = new NoteCommand(personInFilteredList.getName(), descriptor);

        String expectedMessage = String.format(NoteCommand.MESSAGE_EDIT_NOTE_SUCCESS,
                editedPerson.getNote().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personInFilteredList, editedPerson);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonNameUnfilteredList_failure() {
        Name invalidName = new Name("INVALIDNAME");
        NoteDescriptor descriptor = new NoteDescriptorBuilder().withMedications(VALID_MEDICATION_ONE).build();
        NoteCommand editCommand = new NoteCommand(invalidName, descriptor);

        // ensures that Name is not within list
        assertFalse(model.hasName(invalidName));

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonNameFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Name invalidName = new Name("INVALIDNAME");
        // ensures that Name is not within list
        assertFalse(model.hasName(invalidName));

        NoteCommand editCommand = new NoteCommand(invalidName,
                new NoteDescriptorBuilder().build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
    }

    @Test
    public void equals() {
        final NoteCommand standardCommand = new NoteCommand(NAME_FIRST_PERSON, EDIT_AMY);

        // same values -> returns true
        NoteDescriptor copyDescriptor = new NoteDescriptor(EDIT_AMY);
        NoteCommand commandWithSameValues = new NoteCommand(NAME_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(standardCommand.equals(new NoteCommand(NAME_SECOND_PERSON, EDIT_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new NoteCommand(NAME_FIRST_PERSON, EDIT_BOB)));
    }

    @Test
    public void toStringMethod() {
        NoteDescriptor noteDescriptor = new NoteDescriptor();
        NoteCommand noteCommand = new NoteCommand(NAME_FIRST_PERSON, noteDescriptor);
        String expected = NoteCommand.class.getCanonicalName() + "{name=" + NAME_FIRST_PERSON
                          + ", NoteDescriptor=" + noteDescriptor + "}";
        assertEquals(expected, noteCommand.toString());
    }

}

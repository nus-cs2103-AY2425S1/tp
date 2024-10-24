package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Date;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

import java.util.Optional;


/**
 * Contains integration tests (interaction with the Model) and unit tests for DateCommand.
 */
public class DateCommandTest {
    private static final String DATE_STUB = "Some date";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addDate_success() {
        Person personToEdit = ALICE;
        Person editedPerson = new PersonBuilder(personToEdit).withDate(DATE_STUB).build();
        DateCommand dateCommand = new DateCommand(Optional.of(editedPerson.getName().toString()),
                Optional.of(editedPerson.getPhone().toString()), Optional.of(editedPerson.getEmail().toString()),
                new Date(editedPerson.getDate().value));
        String expectedMessage = String.format(DateCommand.MESSAGE_ADD_DATE_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        assertCommandSuccess(dateCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_deleteDate_success() {
        Person personToEdit = ALICE;
        Person editedPerson = new PersonBuilder(personToEdit).withDate("").build();
        DateCommand dateCommand = new DateCommand(Optional.of(editedPerson.getName().toString()),
                Optional.of(editedPerson.getPhone().toString()), Optional.of(editedPerson.getEmail().toString()),
                new Date(editedPerson.getDate().value));
        String expectedMessage = String.format(DateCommand.MESSAGE_DELETE_DATE_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        assertCommandSuccess(dateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personNotFound_failure() {
        // need to assert stranger not in
        DateCommand dateCommand = new DateCommand(Optional.of("Stranger"), Optional.empty(),
                Optional.empty(), new Date(VALID_DATE_BOB));
        assertCommandFailure(dateCommand, model, DateCommand.MESSAGE_NO_PERSON_FOUND);
    }

    @Test
    public void equals() {
        final DateCommand standardCommand = new DateCommand(Optional.of(VALID_NAME_AMY),
                Optional.of(VALID_PHONE_AMY), Optional.of(VALID_EMAIL_AMY), new Date(VALID_DATE_AMY));
        // same values -> returns true
        DateCommand commandWithSameValues = new DateCommand(Optional.of(VALID_NAME_AMY),
                Optional.of(VALID_PHONE_AMY), Optional.of(VALID_EMAIL_AMY), new Date(VALID_DATE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different name -> returns false
        assertFalse(standardCommand.equals(new DateCommand(Optional.of(VALID_NAME_BOB),
                Optional.of(VALID_PHONE_AMY), Optional.of(VALID_EMAIL_AMY), new Date(VALID_DATE_AMY))));
        // different phone -> returns false
        assertFalse(standardCommand.equals(new DateCommand(Optional.of(VALID_NAME_AMY),
                Optional.of(VALID_PHONE_BOB), Optional.of(VALID_EMAIL_AMY), new Date(VALID_DATE_AMY))));
        // different email -> returns false
        assertFalse(standardCommand.equals(new DateCommand(Optional.of(VALID_NAME_AMY),
                Optional.of(VALID_PHONE_AMY), Optional.of(VALID_EMAIL_BOB), new Date(VALID_DATE_AMY))));
        // different date -> returns false
        assertFalse(standardCommand.equals(new DateCommand(Optional.of(VALID_NAME_AMY),
                Optional.of(VALID_PHONE_AMY), Optional.of(VALID_EMAIL_AMY), new Date(VALID_DATE_BOB))));
    }
}

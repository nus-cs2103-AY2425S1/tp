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
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Date;
import seedu.address.model.person.DatePredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for DateCommand.
 */
public class DateCommandTest {
    private static final LocalDateTime DATE_STUB =
            LocalDateTime.of(2025, 6, 24, 14, 15);
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addDate_success() {
        Person personToEdit = ALICE;
        Person editedPerson = new PersonBuilder(personToEdit).withDate(DATE_STUB).build();
        DateCommand dateCommand = new DateCommand(Optional.of(editedPerson.getName().toString()),
                Optional.of(editedPerson.getPhone().toString()), Optional.of(editedPerson.getEmail().toString()),
                new Date(editedPerson.getDate().value));
        String expectedMessage = String.format(DateCommand.MESSAGE_ADD_DATE_SUCCESS, Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        assertCommandSuccess(dateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDate_failure() {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        DatePredicate datePredicate = new DatePredicate(new Date(DATE_STUB));

        Person existingPerson = ALICE;
        Person editedExistingPerson = new PersonBuilder(existingPerson).withDate(DATE_STUB).build();
        expectedModel.setPerson(existingPerson, editedExistingPerson);

        Person overlappingPerson = BOB;
        expectedModel.addPerson(overlappingPerson);
        Person editedOverlappingPerson = new PersonBuilder(overlappingPerson).withDate(DATE_STUB).build();
        DateCommand overlappingDateCommand = new DateCommand(Optional.of(editedOverlappingPerson.getName().toString()),
                Optional.of(editedOverlappingPerson.getPhone().toString()),
                Optional.of(editedOverlappingPerson.getEmail().toString()),
                new Date(DATE_STUB));

        expectedModel.updateFilteredPersonList(datePredicate);

        assertCommandFailure(overlappingDateCommand, expectedModel, DateCommand.MESSAGE_OVERLAPPING_DATES);
    }

    @Test
    public void execute_deleteDate_success() {
        Person personToEdit = ALICE;
        Person editedPerson = new PersonBuilder(personToEdit).withDate(Date.NO_DATE.value).build();
        DateCommand dateCommand = new DateCommand(Optional.of(editedPerson.getName().toString()),
                Optional.of(editedPerson.getPhone().toString()), Optional.of(editedPerson.getEmail().toString()),
                new Date(editedPerson.getDate().value));
        String expectedMessage = String.format(DateCommand.MESSAGE_DELETE_DATE_SUCCESS, Messages.format(editedPerson));
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
    public void execute_multiplePeople_failure() {
        Person existingPersonOne = new PersonBuilder().withName("John").withPhone("91919191")
                .withEmail("john1@gmail.com").withAddress("101 Clementi Road").withTag("High Risk")
                .withAllergies("None").build();
        model.addPerson(existingPersonOne);
        Person existingPersonTwo = new PersonBuilder().withName("John").withPhone("92929292")
                .withEmail("john2@gmail.com").withAddress("102 Clementi Road").withTag("Low Risk").withAllergies("None")
                .build();
        model.addPerson(existingPersonTwo);
        DateCommand dateCommand = new DateCommand(Optional.of("John"), Optional.empty(),
                Optional.empty(), new Date(VALID_DATE_BOB));
        assertCommandFailure(dateCommand, model, DateCommand.MESSAGE_MULTIPLE_PERSONS_FOUND);
    }

    @Test
    public void execute_invalidPhone_failure() {
        DateCommand dateCommand = new DateCommand(Optional.empty(), Optional.of("11111111"),
                Optional.empty(), new Date(VALID_DATE_BOB));
        assertCommandFailure(dateCommand, model, DateCommand.MESSAGE_NO_PERSON_FOUND);
    }

    @Test
    public void execute_invalidEmail_failure() {
        DateCommand dateCommand = new DateCommand(Optional.empty(), Optional.empty(),
                Optional.of("bobexample.com"), new Date(VALID_DATE_BOB));
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

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME;
import static seedu.address.logic.Messages.MESSAGE_UNMARK_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.PredefinedAssignmentsData;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UnmarkCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new PredefinedAssignmentsData());
    }

    @Test
    public void execute_validPersonAndWeek_success() {
        Person personToUnmark = model.getFilteredPersonList().get(0);
        Person markedPerson = new PersonBuilder(personToUnmark).withAttendance(1).buildWithAttendance();
        model.setPerson(personToUnmark, markedPerson);

        UnmarkCommand unmarkCommand = new UnmarkCommand(personToUnmark.getName(), 1);

        Person expectedPerson = new PersonBuilder(personToUnmark).withAttendance().buildWithAttendance();
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                new PredefinedAssignmentsData());
        expectedModel.setPerson(personToUnmark, expectedPerson);

        assertCommandSuccess(unmarkCommand, model, String.format(MESSAGE_UNMARK_SUCCESS,
                personToUnmark.getName(), 1), expectedModel);
    }

    @Test
    public void execute_invalidPersonName_throwsCommandException() {
        Name invalidName = new Name("Invalid Name");
        UnmarkCommand unmarkCommand = new UnmarkCommand(invalidName, 1);

        assertCommandFailure(unmarkCommand, model, String.format(MESSAGE_INVALID_PERSON_DISPLAYED_NAME, invalidName));
    }

    @Test
    public void equals() {
        Name name = new Name("John Doe");
        UnmarkCommand unmarkCommand1 = new UnmarkCommand(name, 1);
        UnmarkCommand unmarkCommand2 = new UnmarkCommand(name, 1);

        // Same object -> returns true
        assertTrue(unmarkCommand1.equals(unmarkCommand1));

        // Same values -> returns true
        assertTrue(unmarkCommand1.equals(unmarkCommand2));

        // Different week -> returns false
        UnmarkCommand unmarkCommandDifferentWeek = new UnmarkCommand(name, 2);
        assertFalse(unmarkCommand1.equals(unmarkCommandDifferentWeek));

        // Different name -> returns false
        UnmarkCommand unmarkCommandDifferentName = new UnmarkCommand(new Name("Jane Doe"), 1);
        assertFalse(unmarkCommand1.equals(unmarkCommandDifferentName));

        // Null -> returns false
        assertFalse(unmarkCommand1.equals(null));

        // Different class -> returns false
        assertFalse(unmarkCommand1.equals(new Object()));
    }

    @Test
    public void toString_validUnmarkCommand_correctStringRepresentation() {
        Name name = new Name("John Doe");
        UnmarkCommand unmarkCommand = new UnmarkCommand(name, 1);

        String expectedString = "UnmarkCommand{name=John Doe, week=1}";

        assertEquals(expectedString, unmarkCommand.toString());
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME;
import static seedu.address.logic.Messages.MESSAGE_MARK_SUCCESS;
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

public class MarkCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new PredefinedAssignmentsData());
    }

    @Test
    public void execute_personMarkedAsPresent_success() throws Exception {
        Person personToMark = model.getFilteredPersonList().get(0);
        MarkCommand markCommand = new MarkCommand(personToMark.getName(), 1);

        Person markedPerson = new PersonBuilder(personToMark).withAttendance(1, 2, 3).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                new PredefinedAssignmentsData());
        expectedModel.setPerson(personToMark, markedPerson);

        assertCommandSuccess(markCommand, model, String.format(MESSAGE_MARK_SUCCESS,
                personToMark.getName(), 1), expectedModel);
    }

    @Test
    public void execute_invalidPersonName_throwsCommandException() {
        Name invalidName = new Name("Non Existent");
        MarkCommand markCommand = new MarkCommand(invalidName, 1);

        assertCommandFailure(markCommand, model, String.format(MESSAGE_INVALID_PERSON_DISPLAYED_NAME, invalidName));
    }

    @Test
    public void equals() {
        Name name = new Name("John Doe");
        MarkCommand markCommand1 = new MarkCommand(name, 1);
        MarkCommand markCommand2 = new MarkCommand(name, 1);

        // Same object -> returns true
        assertTrue(markCommand1.equals(markCommand1));

        // Same values -> returns true
        assertTrue(markCommand1.equals(markCommand2));

        // Different week -> returns false
        MarkCommand unmarkCommandDifferentWeek = new MarkCommand(name, 2);
        assertFalse(markCommand1.equals(unmarkCommandDifferentWeek));

        // Different name -> returns false
        MarkCommand unmarkCommandDifferentName = new MarkCommand(new Name("Jane Doe"), 1);
        assertFalse(markCommand1.equals(unmarkCommandDifferentName));

        // Null -> returns false
        assertFalse(markCommand1.equals(null));

        // Different class -> returns false
        assertFalse(markCommand1.equals(new Object()));
    }

    @Test
    public void toString_validMarkCommand_correctStringRepresentation() {
        Name name = new Name("John Doe");
        MarkCommand markCommand = new MarkCommand(name, 1);

        String expectedString = "MarkCommand{name=John Doe, week=1}";

        assertEquals(expectedString, markCommand.toString());
    }
}

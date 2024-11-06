package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleAssignmentsUtil;
import seedu.address.testutil.TypicalPersons;

/**
 * Tests the functionality of the AddGradeCommand class.
 *
 * This class contains test cases for the constructor and execution of the AddGradeCommand, including tests for invalid inputs
 * such as null values, invalid names, and out-of-range scores, as well as tests for successful execution.
 */
public class AddGradeCommandTest {
    private final Model model = new ModelManager(
            getTypicalAddressBook(),
            new UserPrefs(),
            SampleAssignmentsUtil.getSamplePredefinedAssignments());


    /**
     * Tests that a NullPointerException is thrown when the AddGradeCommand constructor is called with null values.
     */
    @Test
    public void constructor_nullAssignmentFormat_throwsNullPointerException() {

        assertThrows(NullPointerException.class, () -> new AddGradeCommand(null, 0f, null));
    }

    /**
     * Tests that a CommandException is thrown when the assignment name is invalid (does not exist).
     */
    @Test
    public void assignment_invalidName() {
        AddGradeCommand command = new AddGradeCommand(new Name("John Doe"), 0f, "ex10");
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    /**
     * Tests that a CommandException is thrown when the person's name does not match any existing names in the
     * address book.
     */
    @Test
    public void person_invalidName() {
        AddGradeCommand command = new AddGradeCommand(new Name("John DoeDoedoe"), 0f, "ex01");
        assertThrows(CommandException.class, () -> command.execute(model));
    }


    /**
     * Tests that a CommandException is thrown when an assignment score is too high (above 100).
     */
    @Test
    public void assignment_invalidHighScore() {
        AddGradeCommand command = new AddGradeCommand(new Name("John Doe"),
                100f, "ex01");
        assertThrows(CommandException.class, () -> command.execute(model));
    }


    /**
     * Tests that a CommandException is thrown when an assignment score is too low (below 0).
     */
    @Test
    public void assignment_invalidLowScore() {
        AddGradeCommand command = new AddGradeCommand(new Name("John Doe"),
                -1f, "ex01");
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    /**
     * Tests that the AddGradeCommand constructor works correctly with valid inputs.
     */
    @Test
    public void constructor_validAddGradeCommandFormat_success() {
        AddGradeCommand command = new AddGradeCommand(new Name("John Doe"), 9.0f, "Ex09");
        assertNotNull(command);
    }

    /**
     * Tests that the grade for a person is correctly updated when a valid AddGradeCommand is executed.
     *
     * @throws Exception if the command execution fails
     */
    @Test
    public void execute_validPersonGrade_success() throws Exception {
        Person testPerson = TypicalPersons.ALICE;
        final String name = "Ex02";
        AddGradeCommand command = new AddGradeCommand(
                testPerson.getName(),
                9.0f,
                name);
        command.execute(model);
        assertEquals(model
                .getAddressBook()
                .getPersonList()
                .stream().filter(person -> person
                        .getName()
                        .equals(testPerson.getName()))
                .toList().get(0).getAssignment().get(name).getScore(), 9.0f);

    }

    @Test
    public void execute_helpString() throws Exception {
        AddGradeCommand c = AddGradeCommand.showAssignmentDefault();
        assertEquals(
                c.execute(model).getFeedbackToUser(),
                model.getPredefinedAssignments().toString());
    }


}

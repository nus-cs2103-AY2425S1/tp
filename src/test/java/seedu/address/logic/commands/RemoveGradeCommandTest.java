package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.commands.RemoveGradeCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleAssignmentsUtil;

public class RemoveGradeCommandTest {
    private final Model model = new ModelManager(
            getTypicalAddressBook(),
            new UserPrefs(),
            SampleAssignmentsUtil.getSamplePredefinedAssignments());

    @Test
    public void constructor_nullAssignmentFormat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new RemoveGradeCommand(null, null));
    }

    @Test
    public void assignment_invalidName() throws ParseException {
        RemoveGradeCommand command = new RemoveGradeCommand("John Doe", "ex10");
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void person_invalidName() throws ParseException {
        RemoveGradeCommand command = new RemoveGradeCommand("John DoeDoedoe", "ex01");
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void constructor_validRemoveGradeCommandFormat_success() throws ParseException {
        RemoveGradeCommand command = new RemoveGradeCommand("John Doe", "Ex09");
        assertNotNull(command);
    }

    @Test
    public void createPersonWithRemovedGrade_success() {
        Person originalPerson = model.getPerson(new Name("Alice Pauline")).orElseThrow();
        String assignmentToRemove = "Ex01";

        Person updatedPerson = RemoveGradeCommand.createPersonWithRemovedGrade(originalPerson, assignmentToRemove);

        assertFalse(updatedPerson.getAssignment().containsKey(assignmentToRemove));
        assertEquals(originalPerson.getName(), updatedPerson.getName());
        assertEquals(originalPerson.getEmail(), updatedPerson.getEmail());
        assertEquals(originalPerson.getTelegram(), updatedPerson.getTelegram());
        assertEquals(originalPerson.getGithub(), updatedPerson.getGithub());
        assertEquals(originalPerson.getWeeksPresent(), updatedPerson.getWeeksPresent());
        assertEquals(originalPerson.getTags(), updatedPerson.getTags());
    }

    @Test
    public void execute_assignmentDoesNotExist_throwsCommandException() throws ParseException {
        RemoveGradeCommand command =
                new RemoveGradeCommand("Alice Pauline", "NonExistentAssignment");
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_personDoesNotExist_throwsCommandException() throws ParseException {
        RemoveGradeCommand command = new RemoveGradeCommand("NonExistentPerson", "Ex01");
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_assignmentPresent_success() throws Exception {
        AddGradeCommand addedGrade = new AddGradeCommand(new Name("Alice Pauline"), 2.0f, "Ex01");
        addedGrade.execute(model);

        RemoveGradeCommand command = new RemoveGradeCommand("Alice Pauline", "Ex01");
        CommandResult result = command.execute(model);

        assertEquals(String.format(MESSAGE_SUCCESS, "Ex01", "Alice Pauline"), result.getFeedbackToUser());

        Person updatedPerson = model.getPerson(new Name("Alice Pauline")).orElseThrow();
        assertFalse(updatedPerson.getAssignment().containsKey("Ex01"));

    }
    @Test
    public void execute_assignmentAlreadyAbsent_throwsCommandException() throws ParseException {
        RemoveGradeCommand command = new RemoveGradeCommand("Alice Pauline", "Ex01");

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_toString() throws ParseException {
        RemoveGradeCommand c = new RemoveGradeCommand("Alice Pauline", "Ex01");

        String expectedOutput = "Alice Pauline Ex01";
        assertEquals(expectedOutput, c.toString());
    }

    @Test
    public void equals_differentCommands_returnsFalse() throws ParseException {
        RemoveGradeCommand command1 = new RemoveGradeCommand("Alice Pauline", "Ex01");
        RemoveGradeCommand command2 = new RemoveGradeCommand("Alice Pauline", "Ex02");

        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_differentObjectType_returnsFalse() throws ParseException {
        RemoveGradeCommand command = new RemoveGradeCommand("Alice Pauline", "Ex01");
        assertFalse(command.equals("Not a RemoveGradeCommand"));
    }
}

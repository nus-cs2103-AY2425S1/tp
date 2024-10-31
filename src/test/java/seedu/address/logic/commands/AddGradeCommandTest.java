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
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleAssignmentsUtil;
import seedu.address.testutil.TypicalPersons;

public class AddGradeCommandTest {
    private final Model model = new ModelManager(
            getTypicalAddressBook(),
            new UserPrefs(),
            SampleAssignmentsUtil.getSamplePredefinedAssignments());


    @Test
    public void constructor_nullAssignmentFormat_throwsNullPointerException() {

        assertThrows(NullPointerException.class, () -> new AddGradeCommand(null, 0f, null));
    }

    @Test
    public void assignment_invalidName() {
        AddGradeCommand command = new AddGradeCommand("John Doe", 0f, "ex10");
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void person_invalidName() {
        AddGradeCommand command = new AddGradeCommand("John DoeDoedoe", 0f, "ex01");
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void assignment_invalidHighScore() {
        AddGradeCommand command = new AddGradeCommand("John Doe",
                100f, "ex01");
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void assignment_invalidLowScore() {
        AddGradeCommand command = new AddGradeCommand("John Doe",
                -1f, "ex01");
        assertThrows(CommandException.class, () -> command.execute(model));
    }


    @Test
    public void constructor_validAddGradeCommandFormat_success() {
        AddGradeCommand command = new AddGradeCommand("John Doe", 9.0f, "Ex09");
        assertNotNull(command);
    }

    @Test
    public void execute_validPersonGrade_success() throws Exception {
        Person testPerson = TypicalPersons.ALICE;
        final String name = "Ex02";
        AddGradeCommand command = new AddGradeCommand(
                testPerson.getName().toString(),
                9.0f,
                name);
        command.execute(model);
        assertEquals(model
                .getAddressBook()
                .getPersonList()
                .stream().filter(person -> person
                        .getName()
                        .equalIgnoreCase(testPerson.getName()))
                .toList().get(0).getAssignment().get(name).getScore(), 9.0f);

    }


}

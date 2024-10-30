package seedu.academyassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;
import seedu.academyassist.model.sort.SortParam;

/**
 * System tests for SortCommand using different scenarios
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());
    }

    @Test
    public void execute_sortByName_success() {
        SortCommand sortCommand = new SortCommand(new SortParam("name"));
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name");
        expectedModel.sortAcademyAssistByName();

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByClass_success() {
        SortCommand sortCommand = new SortCommand(new SortParam("class"));
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "class");
        expectedModel.sortAcademyAssistByClass();

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortById_success() {
        SortCommand sortCommand = new SortCommand(new SortParam("studentId"));
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "studentId");
        expectedModel.sortAcademyAssistById();

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void constructor_nullSortParam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null));
    }

    @Test
    public void equals_differentCommands_returnsFalse() {
        SortCommand nameCommand = new SortCommand(new SortParam("name"));
        SortCommand classCommand = new SortCommand(new SortParam("class"));
        assertFalse(nameCommand.equals(classCommand));
    }

    @Test
    public void equals_null_returnsFalse() {
        SortCommand command = new SortCommand(new SortParam("name"));
        assertFalse(command.equals(null));
    }
}

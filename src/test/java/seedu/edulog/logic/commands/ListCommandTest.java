package seedu.edulog.logic.commands;

import static seedu.edulog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edulog.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.edulog.testutil.TypicalEdulog.getTypicalEduLog;
import static seedu.edulog.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.edulog.model.Model;
import seedu.edulog.model.ModelManager;
import seedu.edulog.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEduLog(), new UserPrefs());
        expectedModel = new ModelManager(model.getEduLog(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

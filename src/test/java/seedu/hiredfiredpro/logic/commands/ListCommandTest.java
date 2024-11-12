package seedu.hiredfiredpro.logic.commands;

import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.hiredfiredpro.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.hiredfiredpro.testutil.TypicalPersons.getTypicalHiredFiredPro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.ModelManager;
import seedu.hiredfiredpro.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHiredFiredPro(), new UserPrefs());
        expectedModel = new ModelManager(model.getHiredFiredPro(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

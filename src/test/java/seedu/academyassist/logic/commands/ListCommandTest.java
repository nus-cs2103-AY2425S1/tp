package seedu.academyassist.logic.commands;

import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academyassist.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.academyassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());
        expectedModel = new ModelManager(model.getAcademyAssist(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS,
                expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(new ListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS,
                expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(new ListCommand(), model, expectedMessage, expectedModel);
    }
}

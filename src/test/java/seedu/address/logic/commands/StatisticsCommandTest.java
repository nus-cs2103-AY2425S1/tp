package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.StatisticsCommand.MESSAGE_DISPLAY_HIGH_PRIORITY;
import static seedu.address.logic.commands.StatisticsCommand.MESSAGE_DISPLAY_LOW_PRIORITY;
import static seedu.address.logic.commands.StatisticsCommand.MESSAGE_DISPLAY_MEDIUM_PRIORITY;
import static seedu.address.logic.commands.StatisticsCommand.MESSAGE_DISPLAY_TOTAL_PEOPLE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for StatisticsCommand.
 */
public class StatisticsCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsAllStats() {
        String s = String.format(MESSAGE_DISPLAY_TOTAL_PEOPLE, 7) + "\n"
                + String.format(MESSAGE_DISPLAY_HIGH_PRIORITY, 1) + "\n"
                + String.format(MESSAGE_DISPLAY_MEDIUM_PRIORITY, 1) + "\n"
                + String.format(MESSAGE_DISPLAY_LOW_PRIORITY, 5) + "\n";
        assertCommandSuccess(new StatisticsCommand(), model,
                String.format(StatisticsCommand.MESSAGE_DISPLAY_STATISTICS_SUCCESS, s), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showFilteredListStats() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        String s = String.format(MESSAGE_DISPLAY_TOTAL_PEOPLE, 1) + "\n"
                + String.format(MESSAGE_DISPLAY_HIGH_PRIORITY, 1) + "\n"
                + String.format(MESSAGE_DISPLAY_MEDIUM_PRIORITY, 0) + "\n"
                + String.format(MESSAGE_DISPLAY_LOW_PRIORITY, 0) + "\n";
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        assertCommandSuccess(new StatisticsCommand(), model,
                String.format(StatisticsCommand.MESSAGE_DISPLAY_STATISTICS_SUCCESS, s), expectedModel);
    }
}

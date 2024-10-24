package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class ViewTutorChartCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandResult expectedCommandResult = new CommandResult(ViewTutorChartCommand.MESSAGE_SUCCESS, false, false,
                true, model.getFilteredPersonList().toArray(new Person[0]));

        assertCommandSuccess(new ViewTutorChartCommand(), model, commandHistory,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        ViewTutorChartCommand viewTutorChartCommand = new ViewTutorChartCommand();
        // same object -> returns true
        assert(viewTutorChartCommand.equals(viewTutorChartCommand));
        // same values -> returns true
        ViewTutorChartCommand viewTutorChartCommandCopy = new ViewTutorChartCommand();
        assert(viewTutorChartCommand.equals(viewTutorChartCommandCopy));
        // different types -> returns false
        assert(!viewTutorChartCommand.equals(1));
        // null -> returns false
        assert(!viewTutorChartCommand.equals(null));
    }

}

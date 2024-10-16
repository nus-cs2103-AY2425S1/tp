package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ViewTasksCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UiStateTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sameCommandTypes_returnsSameUiState() throws CommandException {
        new AddCommand(AMY).execute(expectedModel);
        new DeleteCommand(INDEX_FIRST_PERSON).execute(model);
        assertEquals(expectedModel.getUiState().getState(), model.getUiState().getState());
    }

    @Test
    public void execute_differentCommandTypes_returnsDifferentUiState() throws CommandException {
        new AddCommand(AMY).execute(expectedModel);
        new ViewTasksCommand().execute(model);
        assertNotEquals(expectedModel.getUiState().getState(), model.getUiState().getState());
    }
}

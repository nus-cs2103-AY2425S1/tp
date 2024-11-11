package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RandomCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_random_showsOnePerson() {
        try {
            CommandResult result = new RandomCommand().execute(model);

            // First check that CommandResult is a success
            assertEquals(new CommandResult(RandomCommand.MESSAGE_SUCCESS + "\n", false, false), result);

            // Check that model updates with only one person in the filteredlist
            // We do not check if displayed person is truly random since that uses java.util.random which is already
            // well-tested.
            assertEquals(1, model.getFilteredPersonList().size());

        } catch (CommandException e) {
            throw new AssertionError("This should not happen.", e);
        }
    }

    @Test
    public void execute_onePersonInList_throwsException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandFailure(new RandomCommand(), model, RandomCommand.MESSAGE_RANDOM_INSUFFICIENT_STUDENTS);
    }
}

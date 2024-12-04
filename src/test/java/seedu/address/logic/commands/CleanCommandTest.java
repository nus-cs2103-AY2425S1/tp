
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.GradYear;
import seedu.address.model.person.GradYearPredicate;
import seedu.address.model.person.Person;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code CleanCommand}.
 */

public class CleanCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_clean_success() {
        CommandResult expectedCommandResult = new CommandResult(CleanCommand.MESSAGE_CLEAN_SUCCESS);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<Person> lastShownList = expectedModel.getFilteredPersonList();
        int listSize = lastShownList.size();
        for (int i = listSize - 1; i >= 0; i--) {
            Person p = lastShownList.get(i);
            Optional<GradYear> graduationYear = p.getGradYear();
            if (graduationYear.isPresent()) {
                String year = graduationYear.get().toString();
                Integer yearValue = Integer.parseInt(year);
                if (yearValue < Year.now().getValue()) {
                    expectedModel.deletePerson(p);
                }
            }
        }

        assertCommandSuccess(new CleanCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_alreadyCleaned_throwsCommandException() {
        ModelManager testModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<Person> lastShownList = testModel.getFilteredPersonList();
        int listSize = lastShownList.size();
        for (int i = listSize - 1; i >= 0; i--) {
            Person p = lastShownList.get(i);
            Optional<GradYear> graduationYear = p.getGradYear();
            if (graduationYear.isPresent()) {
                String year = graduationYear.get().toString();
                Integer yearValue = Integer.parseInt(year);
                if (yearValue < Year.now().getValue()) {
                    testModel.deletePerson(p);
                }
            }
        }

        assertCommandFailure(new CleanCommand(), testModel, CleanCommand.MESSAGE_ALREADY_CLEANED);
    }

    @Test
    public void undo_commandExecuted_success() throws Exception {
        CommandResult expectedCommandResult = new CommandResult(CleanCommand.MESSAGE_UNDO_SUCCESS);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        CleanCommand cleanCommand = new CleanCommand();
        cleanCommand.execute(model);
        CommandResult undoResult = cleanCommand.undo(model);
        assertEquals(expectedCommandResult, undoResult);
        assertEquals(expectedModel, model);
    }

    @Test
    public void undo_commandNotExecuted_throwsAssertionError() {
        CleanCommand cleanCommand = new CleanCommand();
        assertThrows(AssertionError.class, Command.MESSAGE_NOT_EXECUTED_ERROR, () -> cleanCommand.undo(model));
    }

    @Test
    public void equals() {
        CleanCommand cleanFirstCommand = new CleanCommand();
        CleanCommand cleanSecondCommand = new CleanCommand();

        // same object -> returns true
        assertTrue(cleanFirstCommand.equals(cleanFirstCommand));

        // same values -> returns true
        assertTrue(cleanFirstCommand.equals(cleanSecondCommand));

        // different types -> returns false
        assertFalse(cleanFirstCommand.equals(1));

        // null -> returns false
        assertFalse(cleanFirstCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        CleanCommand cleanCommand = new CleanCommand();
        GradYear presentYear = new GradYear(String.valueOf(Year.now().getValue()));
        GradYearPredicate predicate = new GradYearPredicate(presentYear);

        String expected = CleanCommand.class.getCanonicalName() + "{graduation date predicate=" + predicate + "}";
        assertEquals(expected, cleanCommand.toString());
    }
}


package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureWithNewList;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalClientHub;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewCommand}.
 */
public class ViewCommandTest {
    private Model model = new ModelManager(getTypicalClientHub(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClientHub(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(List.of("Alice"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(List.of("Bob"));

        ViewCommand viewCommandFirstCommand = new ViewCommand(firstPredicate);
        ViewCommand viewCommandSecondCommand = new ViewCommand(secondPredicate);

        // same object -> returns true
        assertTrue(viewCommandFirstCommand.equals(viewCommandFirstCommand));

        // same values -> returns true
        ViewCommand viewCommandFirstCommandCopy = new ViewCommand(firstPredicate);
        assertTrue(viewCommandFirstCommand.equals(viewCommandFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewCommandFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewCommandFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewCommandFirstCommand.equals(viewCommandSecondCommand));
    }

    @Test
    public void execute_singleKeyword_onePersonFound() {
        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW, 1), false, true, false);
        NameContainsKeywordsPredicate predicate = preparePredicate("Benson Meier");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getDisplayPersons());
    }

    @Test
    public void execute_partialKeyword_onePersonFound() {
        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW, 1), false, true, false);
        NameContainsKeywordsPredicate predicate = preparePredicate("Ben");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getDisplayPersons());
    }

    @Test
    public void execute_multipleSpaces_throwsCommandException() {
        NameContainsKeywordsPredicate predicate = preparePredicate("  ");
        ViewCommand command = new ViewCommand(predicate);
        assertCommandFailureWithNewList(command, "  ", model, String.format(
                MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW + ViewCommand.NO_PERSON_FOUND_VIEW_MESSAGE, 0));
    }

    @Test
    public void execute_singleKeywordNoPersonFound_throwsCommandException() {
        NameContainsKeywordsPredicate predicate = preparePredicate("NonExistentName");
        ViewCommand command = new ViewCommand(predicate);
        assertCommandFailureWithNewList(command, "NonExistentName", model, String.format(
                MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW + ViewCommand.NO_PERSON_FOUND_VIEW_MESSAGE, 0));
    }

    @Test
    public void execute_singleKeywordTwoPersonFound_throwsCommandException() {
        NameContainsKeywordsPredicate predicate = preparePredicate("Alice");
        ViewCommand command = new ViewCommand(predicate);
        assertCommandFailureWithNewList(command, "Alice", model, String.format(
                MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW + ViewCommand.MORE_THAN_ONE_PERSON_VIEW_MESSAGE, 2));
    }

    /**
     * Tests that ViewCommand throws a CommandException when the surname prefix search ("K")
     * matches multiple people in the address book (e.g., "Fiona Kunz" and "Carl Kurz").
     * Since the prefix of ths surname of Fiona Kunz and Carl Kurz is "K", the search will return both of them.
     */
    @Test
    public void execute_surnameKeywordTwoPersonFound_throwsCommandException() {
        NameContainsKeywordsPredicate predicate = preparePredicate("K");
        ViewCommand command = new ViewCommand(predicate);
        assertCommandFailureWithNewList(command, "K", model, String.format(
                MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW + ViewCommand.MORE_THAN_ONE_PERSON_VIEW_MESSAGE, 2));
    }

    @Test
    public void execute_mixedCaseFullName_success() {
        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW, 1), false, true, false);
        NameContainsKeywordsPredicate predicate = preparePredicate("bEnSoN mEiEr");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getDisplayPersons());
    }


    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(List.of("Alice"));
        ViewCommand findClientTypeCommand = new ViewCommand(predicate);
        String expected = ViewCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findClientTypeCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        // Split the input string by whitespaces
        String[] keywords = userInput.split("\\s+");
        return new NameContainsKeywordsPredicate(List.of(keywords));
    }
}

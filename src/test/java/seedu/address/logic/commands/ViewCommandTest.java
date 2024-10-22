package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICEY;
import static seedu.address.testutil.TypicalPersons.getTypicalClientHub;

import java.util.Arrays;
import java.util.Collections;
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
    public void execute_singleKeyword_noPersonFound() {
        CommandResult expectedCommandResult = new CommandResult(
                String.format(
                        MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW + "\nPlease specify the name further to view.", 0),
                false, false, false);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_twoPersonFound() {
        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2)
                        + "\nDuplicates found. Please specify the name further.",
                false, false, false);
        NameContainsKeywordsPredicate predicate = preparePredicate("Alice");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(ALICE, ALICEY), model.getFilteredPersonList());
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
        return new NameContainsKeywordsPredicate(List.of(userInput));
    }
}

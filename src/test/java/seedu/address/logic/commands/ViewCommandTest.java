package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonFulfilsPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewCommand.
 */
public class ViewCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_zeroKeywords_showsEverything() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        PersonFulfilsPredicate predicate = preparePredicate("");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidKeyword_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonFulfilsPredicate predicate = preparePredicate("hello");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void equals() {
        PersonFulfilsPredicate firstPredicate = new PersonFulfilsPredicate("");
        PersonFulfilsPredicate secondPredicate = new PersonFulfilsPredicate("buyer");

        ViewCommand viewFirstCommand = new ViewCommand(firstPredicate);
        ViewCommand viewSecondCommand = new ViewCommand(secondPredicate);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(firstPredicate);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different keyword -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void toStringMethod() {
        PersonFulfilsPredicate predicate = new PersonFulfilsPredicate("buyer");
        ViewCommand viewCommand = new ViewCommand(predicate);
        String expected = ViewCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code PersonFulfilsPredicate}.
     */
    private PersonFulfilsPredicate preparePredicate(String userInput) {
        String trimmedArgs = userInput.trim();
        String keyword = trimmedArgs.isEmpty() ? "" : trimmedArgs.split("\\s+")[0];
        return new PersonFulfilsPredicate(keyword);
    }
}

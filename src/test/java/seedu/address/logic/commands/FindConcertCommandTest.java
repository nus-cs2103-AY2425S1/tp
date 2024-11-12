package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CONCERTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBookConcerts;
import static seedu.address.testutil.TypicalConcerts.COACHELLA;
import static seedu.address.testutil.TypicalConcerts.GLASTONBURY;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.commons.NameContainsKeywordsPredicate;
import seedu.address.model.concert.Concert;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FindConcertCommand}.
 */
public class FindConcertCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookConcerts(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBookConcerts(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate<Concert> firstPredicate = new NameContainsKeywordsPredicate<>(
                Collections.singletonList("first"));
        NameContainsKeywordsPredicate<Concert> secondPredicate = new NameContainsKeywordsPredicate<>(
                Collections.singletonList("second"));

        FindConcertCommand findFirstCommand = new FindConcertCommand(firstPredicate);
        FindConcertCommand findSecondCommand = new FindConcertCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindConcertCommand findFirstCommandCopy = new FindConcertCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different concert -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroNameKeywords_noConcertFound() {
        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_CONCERTS_LISTED_OVERVIEW, 0),
                false, true, false,
                false, false, false);
        NameContainsKeywordsPredicate<Concert> predicate = prepareNamePredicate(" ");
        FindConcertCommand command = new FindConcertCommand(predicate);
        expectedModel.updateFilteredConcertList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredConcertList());
    }

    @Test
    public void execute_multipleNameKeywords_multipleConcertsFound() {
        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_CONCERTS_LISTED_OVERVIEW, 2),
                false, true, false,
                false, false, false);
        NameContainsKeywordsPredicate<Concert> predicate = prepareNamePredicate("Coachella Glastonbury");
        FindConcertCommand command = new FindConcertCommand(predicate);
        expectedModel.updateFilteredConcertList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Arrays.asList(COACHELLA, GLASTONBURY), model.getFilteredConcertList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate<Concert> predicate =
                new NameContainsKeywordsPredicate<>(Arrays.asList("keyword"));
        FindConcertCommand command = new FindConcertCommand(predicate);
        String expected = FindConcertCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate<Concert> prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }

}


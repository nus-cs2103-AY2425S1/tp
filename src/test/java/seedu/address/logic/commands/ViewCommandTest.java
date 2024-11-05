package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_NAME_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.CARLDUH;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.getAdditionalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameMatchesKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class ViewCommandTest {
    private Model model = new ModelManager(getAdditionalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getAdditionalAddressBook(), new UserPrefs());

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_NAME_OVERVIEW, 0, "");
        NameMatchesKeywordPredicate predicate = preparePredicate(" ");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleWordInKeyword_partialNameMatched() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_NAME_OVERVIEW,
                2, KEYWORD_MATCHING_MEIER);
        NameMatchesKeywordPredicate predicate = preparePredicate(KEYWORD_MATCHING_MEIER);
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleWordsInKeyword_nameMatched() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_NAME_OVERVIEW,
                2, "Carl Kurz");
        NameMatchesKeywordPredicate predicate = preparePredicate("Carl Kurz");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, CARLDUH), model.getFilteredPersonList());

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_NAME_OVERVIEW,
                1, "Carl Duh Kurz");
        predicate = preparePredicate("Carl Duh Kurz");
        command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARLDUH), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleSpacesInKeyword_personFound() {
        // Multiple spaces between keywords should be treated as a single space
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_NAME_OVERVIEW,
                2, "Carl Kurz");
        NameMatchesKeywordPredicate predicate = preparePredicate("Carl      Kurz");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, CARLDUH), model.getFilteredPersonList());
    }

    @Test
    public void execute_mixedCaseKeyword_personFound() {
        // Case insensitive matching test
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_NAME_OVERVIEW,
                2, "cArL kUrZ");
        NameMatchesKeywordPredicate predicate = preparePredicate("cArL kUrZ");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, CARLDUH), model.getFilteredPersonList());
    }

    @Test
    public void equals() {
        NameMatchesKeywordPredicate firstPredicate =
                preparePredicate("first");
        NameMatchesKeywordPredicate secondPredicate =
                preparePredicate("second");

        ViewCommand findFirstCommand = new ViewCommand(firstPredicate);
        ViewCommand findSecondCommand = new ViewCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ViewCommand findFirstCommandCopy = new ViewCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void toStringMethod() {
        NameMatchesKeywordPredicate predicate = new NameMatchesKeywordPredicate(Arrays.asList("keyword"));
        ViewCommand viewCommand = new ViewCommand(predicate);
        String expected = ViewCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewCommand.toString());
    }


    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameMatchesKeywordPredicate preparePredicate(String userInput) {
        return new NameMatchesKeywordPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

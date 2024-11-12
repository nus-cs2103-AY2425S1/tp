package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_FOUND_INTEREST;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_PERSON_FOUND_INTEREST;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.InterestContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindByInterestCommand}.
 */
public class FindByInterestCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        InterestContainsKeywordsPredicate firstPredicate = new InterestContainsKeywordsPredicate("reading");
        InterestContainsKeywordsPredicate secondPredicate = new InterestContainsKeywordsPredicate("sports");

        FindByInterestCommand findFirstCommand = new FindByInterestCommand(firstPredicate);
        FindByInterestCommand findSecondCommand = new FindByInterestCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindByInterestCommand findFirstCommandCopy = new FindByInterestCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        InterestContainsKeywordsPredicate predicate = new InterestContainsKeywordsPredicate(" ");
        FindByInterestCommand command = new FindByInterestCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_onePersonFound() {
        String expectedMessage = MESSAGE_PERSON_FOUND_INTEREST;
        InterestContainsKeywordsPredicate predicate = new InterestContainsKeywordsPredicate("football");
        FindByInterestCommand command = new FindByInterestCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_FOUND_INTEREST, 2);
        InterestContainsKeywordsPredicate predicate = new InterestContainsKeywordsPredicate("reading");
        FindByInterestCommand command = new FindByInterestCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_noInterestsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        InterestContainsKeywordsPredicate predicate = new InterestContainsKeywordsPredicate("journaling");
        FindByInterestCommand command = new FindByInterestCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }


    @Test
    public void toStringMethod() {
        InterestContainsKeywordsPredicate predicate = new InterestContainsKeywordsPredicate("reading");

        FindByInterestCommand findCommand = new FindByInterestCommand(predicate);
        String expected = FindByInterestCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }
}

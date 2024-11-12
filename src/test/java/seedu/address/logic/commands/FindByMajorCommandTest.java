package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_FOUND_MAJOR;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MajorContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindByMajorCommand}.
 */
public class FindByMajorCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        MajorContainsKeywordsPredicate firstPredicate =
                new MajorContainsKeywordsPredicate("Computer Science");
        MajorContainsKeywordsPredicate secondPredicate =
                new MajorContainsKeywordsPredicate("Information Technology");

        FindByMajorCommand findFirstCommand = new FindByMajorCommand(firstPredicate);
        FindByMajorCommand findSecondCommand = new FindByMajorCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindByMajorCommand findFirstCommandCopy = new FindByMajorCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different major -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate("  ");
        FindByMajorCommand command = new FindByMajorCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_FOUND_MAJOR, 3, "Computer Science");
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate("Computer Science");
        FindByMajorCommand command = new FindByMajorCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate("Computer Science");
        FindByMajorCommand findCommand = new FindByMajorCommand(predicate);
        String expected = FindByMajorCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }
}

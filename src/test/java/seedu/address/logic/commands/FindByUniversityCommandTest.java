package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_FOUND_UNIVERSITY;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.UniversityContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindByUniversityCommand}.
 */
public class FindByUniversityCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests if the equals() method correctly identifies equivalent and non-equivalent commands.
     */
    @Test
    public void equals() {
        UniversityContainsKeywordsPredicate firstPredicate =
                new UniversityContainsKeywordsPredicate("NUS");
        UniversityContainsKeywordsPredicate secondPredicate =
                new UniversityContainsKeywordsPredicate("NTU");

        FindByUniversityCommand findFirstCommand = new FindByUniversityCommand(firstPredicate);
        FindByUniversityCommand findSecondCommand = new FindByUniversityCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindByUniversityCommand findFirstCommandCopy = new FindByUniversityCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different university -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * Tests if the command execution with zero keywords finds no matching persons.
     */
    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        UniversityContainsKeywordsPredicate predicate = new UniversityContainsKeywordsPredicate(" ");
        FindByUniversityCommand command = new FindByUniversityCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    /**
     * Tests if the command execution with a single valid keyword finds multiple matching persons.
     */
    @Test
    public void execute_singleKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_FOUND_UNIVERSITY, 4, "NUS");
        UniversityContainsKeywordsPredicate predicate = new UniversityContainsKeywordsPredicate("NUS");
        FindByUniversityCommand command = new FindByUniversityCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    /**
     * Tests if the toString method provides the correct string representation of the command.
     */
    @Test
    public void toStringMethod() {
        UniversityContainsKeywordsPredicate predicate = new UniversityContainsKeywordsPredicate("NUS");
        FindByUniversityCommand findCommand = new FindByUniversityCommand(predicate);
        String expected = FindByUniversityCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }
}

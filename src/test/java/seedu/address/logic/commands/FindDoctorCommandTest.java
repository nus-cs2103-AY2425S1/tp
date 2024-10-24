package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.address.model.person.DoctorNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindDoctorCommand}.
 */
public class FindDoctorCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        DoctorNameContainsKeywordsPredicate firstPredicate =
                new DoctorNameContainsKeywordsPredicate(Collections.singletonList("first"));
        DoctorNameContainsKeywordsPredicate secondPredicate =
                new DoctorNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindDoctorCommand findFirstCommand = new FindDoctorCommand(firstPredicate);
        FindDoctorCommand findSecondCommand = new FindDoctorCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindDoctorCommand findFirstCommandCopy = new FindDoctorCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DoctorNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindDoctorCommand command = new FindDoctorCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        DoctorNameContainsKeywordsPredicate predicate = preparePredicate("Po Wong Bing");
        FindDoctorCommand command = new FindDoctorCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        DoctorNameContainsKeywordsPredicate predicate = new DoctorNameContainsKeywordsPredicate(
            Arrays.asList("keyword"));
        FindDoctorCommand findDoctorCommand = new FindDoctorCommand(predicate);
        String expected = FindDoctorCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findDoctorCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code DoctorNameContainsKeywordsPredicate}.
     */
    private DoctorNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DoctorNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

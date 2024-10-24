package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.WorkExperienceContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindByUniversityCommand}.
 */
public class FindByWorkExperienceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        WorkExperienceContainsKeywordsPredicate firstPredicate =
                new WorkExperienceContainsKeywordsPredicate("Intern", "Google", "2024");
        WorkExperienceContainsKeywordsPredicate secondPredicate =
                new WorkExperienceContainsKeywordsPredicate("Engineer", "Tesla", "2023");

        FindByWorkExperienceCommand findFirstCommand = new FindByWorkExperienceCommand(firstPredicate);
        FindByWorkExperienceCommand findSecondCommand = new FindByWorkExperienceCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindByWorkExperienceCommand findFirstCommandCopy = new FindByWorkExperienceCommand(firstPredicate);
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
        WorkExperienceContainsKeywordsPredicate predicate = new WorkExperienceContainsKeywordsPredicate("", "", "");
        FindByWorkExperienceCommand command = new FindByWorkExperienceCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        WorkExperienceContainsKeywordsPredicate predicate = new WorkExperienceContainsKeywordsPredicate("Intern",
                "Google", "2024");
        FindByWorkExperienceCommand command = new FindByWorkExperienceCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());

    }
    /**
     * Parses {@code role}, {@code company}, and {@code year} into a {@code WorkExpContainsKeywordsPredicate}.
     */
    private WorkExperienceContainsKeywordsPredicate preparePredicate(String role, String company, String year) {
        return new WorkExperienceContainsKeywordsPredicate(role, company, year);
    }
}

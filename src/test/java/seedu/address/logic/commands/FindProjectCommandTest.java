package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PROJECTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProjects.ALPHA;
import static seedu.address.testutil.TypicalProjects.BETA;
import static seedu.address.testutil.TypicalProjects.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindProjectCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ProjectNameContainsKeywordsPredicate firstPredicate =
                new ProjectNameContainsKeywordsPredicate(Collections.singletonList("first"));
        ProjectNameContainsKeywordsPredicate secondPredicate =
                new ProjectNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindProjectCommand findProjectFirstCommand = new FindProjectCommand(firstPredicate);
        FindProjectCommand findProjectSecondCommand = new FindProjectCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findProjectFirstCommand.equals(findProjectFirstCommand));

        // same values -> returns true
        FindProjectCommand findProjectFirstCommandCopy = new FindProjectCommand(firstPredicate);
        assertTrue(findProjectFirstCommand.equals(findProjectFirstCommandCopy));

        // different types -> returns false
        assertFalse(findProjectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findProjectFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(findProjectFirstCommand.equals(findProjectSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noProjectFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 0);
        ProjectNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindProjectCommand command = new FindProjectCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredProjectList());
    }

    @Test
    public void execute_multipleKeywords_multipleProjectsFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 2);
        ProjectNameContainsKeywordsPredicate predicate = preparePredicate("Alpha Beta");
        FindProjectCommand command = new FindProjectCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALPHA, BETA), model.getFilteredProjectList());
    }

    @Test
    public void toStringMethod() {
        ProjectNameContainsKeywordsPredicate predicate =
                new ProjectNameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindProjectCommand findProjectCommand = new FindProjectCommand(predicate);
        String expected = FindProjectCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findProjectCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private ProjectNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ProjectNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_NO_PERSON_FOUND_FOR_VIEW;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureWithNewList;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalClientHub;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ClientTypeContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindClientTypeCommand}.
 */
public class FindClientTypeCommandTest {
    private Model model = new ModelManager(getTypicalClientHub(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClientHub(), new UserPrefs());

    @Test
    public void equals() {
        ClientTypeContainsKeywordsPredicate firstPredicate =
                new ClientTypeContainsKeywordsPredicate(List.of("Investment"));
        ClientTypeContainsKeywordsPredicate secondPredicate =
                new ClientTypeContainsKeywordsPredicate(List.of("Healthcare"));

        FindClientTypeCommand findClientTypeFirstCommand = new FindClientTypeCommand(firstPredicate);
        FindClientTypeCommand findClientTypeSecondCommand = new FindClientTypeCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findClientTypeFirstCommand.equals(findClientTypeFirstCommand));

        // same values -> returns true
        FindClientTypeCommand findClientTypeFirstCommandCopy = new FindClientTypeCommand(firstPredicate);
        assertTrue(findClientTypeFirstCommand.equals(findClientTypeFirstCommandCopy));

        // different types -> returns false
        assertFalse(findClientTypeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findClientTypeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findClientTypeFirstCommand.equals(findClientTypeSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noClientFound() {
        String expectedMessage = String.format(MESSAGE_NO_PERSON_FOUND_FOR_VIEW);
        String userInput = "  ";
        ClientTypeContainsKeywordsPredicate predicate = preparePredicate(userInput);
        FindClientTypeCommand command = new FindClientTypeCommand(predicate);
        assertCommandFailureWithNewList(command, userInput, model, expectedMessage);
    }

    @Test
    public void execute_singleKeyword_multipleClientsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ClientTypeContainsKeywordsPredicate predicate = preparePredicate("Investment");
        FindClientTypeCommand command = new FindClientTypeCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL), model.getDisplayPersons());
    }

    @Test
    public void execute_caseInsensitiveKeyword_multipleClientsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ClientTypeContainsKeywordsPredicate predicate = preparePredicate("inVeStMeNt");
        FindClientTypeCommand command = new FindClientTypeCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL), model.getDisplayPersons());
    }

    @Test
    public void execute_multipleMatchingKeywords_singleClientFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        ClientTypeContainsKeywordsPredicate predicate = preparePredicate("Investment Healthcare");
        FindClientTypeCommand command = new FindClientTypeCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getDisplayPersons());
    }

    @Test
    public void execute_secondWordMatch_returnsCorrectClient() {
        // Should match person with client type "Investment Plan" when searching just "Plan"
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        ClientTypeContainsKeywordsPredicate predicate = preparePredicate("Savings");
        FindClientTypeCommand command = new FindClientTypeCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getDisplayPersons());
    }

    //    @Test
    //    public void execute_multipleKeywordsMatchingSingleClientType_singleClientFound() {
    //        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
    //        ClientTypeContainsKeywordsPredicate predicate = preparePredicate("Insurance Plan");
    //        FindClientTypeCommand command = new FindClientTypeCommand(predicate);
    //        expectedModel.updateFilteredPersonList(predicate);
    //        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //        assertEquals(Arrays.asList(HARRY), model.getDisplayPersons());
    //    }


    @Test
    public void toStringMethod() {
        ClientTypeContainsKeywordsPredicate predicate = new ClientTypeContainsKeywordsPredicate(List.of("Investment"));
        FindClientTypeCommand findClientTypeCommand = new FindClientTypeCommand(predicate);
        String expected = FindClientTypeCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findClientTypeCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ClientTypeContainsKeywordsPredicate}.
     */
    private ClientTypeContainsKeywordsPredicate preparePredicate(String userInput) {
        String[] keywords = userInput.split("\\s+");
        return new ClientTypeContainsKeywordsPredicate(List.of(keywords));
    }
}

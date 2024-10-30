package seedu.address.logic.commands.findcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.findcommand.FindAddressCommand.MESSAGE_FIND_ADDRESS_PERSON_SUCCESS;
import static seedu.address.logic.commands.findcommand.FindCommand.MESSAGE_FIND_PERSON_UNSUCCESSFUL;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.keywordspredicate.AddressContainsKeywordsPredicate;

public class FindAddressCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("Bedok Block 16 #04-02"));
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("Ang Mo Kio Street 10"));

        FindAddressCommand findFirstCommand = new FindAddressCommand(firstPredicate);
        FindAddressCommand findSecondCommand = new FindAddressCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindAddressCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_fullMatch_singlePersonFound() {
        AddressContainsKeywordsPredicate predicate = preparePredicate("311, Clementi Ave 2, #02-25");
        String expectedMessage = String.format(MESSAGE_FIND_ADDRESS_PERSON_SUCCESS, predicate.getDisplayString());
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialMatch_singlePersonFound() {
        AddressContainsKeywordsPredicate predicate = preparePredicate("Jurong");
        String expectedMessage = String.format(MESSAGE_FIND_ADDRESS_PERSON_SUCCESS, predicate.getDisplayString());
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialMatch2_singlePersonFound() {
        AddressContainsKeywordsPredicate predicate = preparePredicate("wall str");
        String expectedMessage = String.format(MESSAGE_FIND_ADDRESS_PERSON_SUCCESS, predicate.getDisplayString());
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialMatch_multiplePersonsFound() {
        AddressContainsKeywordsPredicate predicate = preparePredicate("ave");
        String expectedMessage = String.format(MESSAGE_FIND_ADDRESS_PERSON_SUCCESS, predicate.getDisplayString());
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void execute_absentPartialMatchKeyword_noPersonFound() {
        AddressContainsKeywordsPredicate predicate = preparePredicate("x");
        String expectedMessage = MESSAGE_FIND_PERSON_UNSUCCESSFUL;
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_absentPartialMatchKeyword2_noPersonFound() {
        AddressContainsKeywordsPredicate predicate = preparePredicate("834 Bukit Batok");
        String expectedMessage = MESSAGE_FIND_PERSON_UNSUCCESSFUL;
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Arrays.asList("34 Tanah Merah"));
        FindAddressCommand findPhoneCommand = new FindAddressCommand(predicate);
        String expected = FindAddressCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findPhoneCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code AddressContainsKeywordsPredicate}.
     */
    private AddressContainsKeywordsPredicate preparePredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(Arrays.asList(userInput));
    }
}

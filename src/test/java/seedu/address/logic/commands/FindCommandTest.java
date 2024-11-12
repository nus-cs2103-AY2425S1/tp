package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReminderAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CompoundedPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.OrgContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ReminderAddressBook());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ReminderAddressBook());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        OrgContainsKeywordsPredicate firstOrgPredicate = new OrgContainsKeywordsPredicate(
                Collections.singletonList("NUS"));
        OrgContainsKeywordsPredicate secondOrgPredicate = new OrgContainsKeywordsPredicate(
                Collections.singletonList("NTU"));;

        FindCommand findFirstCommand = new FindCommand(new CompoundedPredicate(firstNamePredicate, firstOrgPredicate));
        FindCommand findSecondCommand = new FindCommand(
                new CompoundedPredicate(secondNamePredicate, secondOrgPredicate));
        FindCommand findThirdCommand = new FindCommand(new CompoundedPredicate(firstNamePredicate, secondOrgPredicate));

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(
                new CompoundedPredicate(firstNamePredicate, firstOrgPredicate));
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // same name but different org -> returns false
        assertFalse(findFirstCommand.equals(findThirdCommand));
    }

    @Test
    public void execute_zeroKeywords_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        CompoundedPredicate predicate = preparePredicate(new String[]{" "}, new String[]{" "});
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        CompoundedPredicate predicate = preparePredicate(new String[]{"Kurz" , "Elle", "Kunz"}, new String[]{""});
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private CompoundedPredicate preparePredicate(String[] nameInputs, String[] orgInputs) {
        return new CompoundedPredicate(new NameContainsKeywordsPredicate(Arrays.asList(nameInputs)),
                new OrgContainsKeywordsPredicate(Arrays.asList(orgInputs)));
    }
}

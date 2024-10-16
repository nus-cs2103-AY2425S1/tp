package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.owner.OwnerNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.TypicalOwners;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    private Model ownerModel = new ModelManager(TypicalOwners.getTypicalAddressBook(), new UserPrefs());
    private Model expectedOwnerModel = new ModelManager(TypicalOwners.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        // Test for persons
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // Test for owners
        OwnerNameContainsKeywordsPredicate firstOwnerPredicate =
                new OwnerNameContainsKeywordsPredicate(Collections.singletonList("first"));
        OwnerNameContainsKeywordsPredicate secondOwnerPredicate =
                new OwnerNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findOwnerFirstCommand = new FindCommand(firstOwnerPredicate);
        FindCommand findOwnerSecondCommand = new FindCommand(secondOwnerPredicate);

        // same object -> returns true
        assertTrue(findOwnerFirstCommand.equals(findOwnerFirstCommand));

        // same values -> returns true
        FindCommand findOwnerFirstCommandCopy = new FindCommand(firstOwnerPredicate);
        assertTrue(findOwnerFirstCommand.equals(findOwnerFirstCommandCopy));

        // different types -> returns false
        assertFalse(findOwnerFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findOwnerFirstCommand.equals(null));

        // different owners -> returns false
        assertFalse(findOwnerFirstCommand.equals(findOwnerSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroKeywords_noOwnerFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        OwnerNameContainsKeywordsPredicate predicate = prepareOwnerPredicate(" ");
        FindCommand command = new FindCommand(predicate); // 'true' indicates an owner search
        expectedOwnerModel.updateFilteredOwnerList(predicate);
        assertCommandSuccess(command, ownerModel, expectedMessage, expectedOwnerModel);
        assertEquals(Collections.emptyList(), ownerModel.getFilteredOwnerList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate); // 'false' for person search
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multipleOwnersFound() {
        String expectedOwnerMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        OwnerNameContainsKeywordsPredicate ownerPredicate = prepareOwnerPredicate("Kurz Elle Kunz");
        FindCommand commandOwner = new FindCommand(ownerPredicate); // 'true' for owner search
        expectedOwnerModel.updateFilteredOwnerList(ownerPredicate);
        assertCommandSuccess(commandOwner, ownerModel, expectedOwnerMessage, expectedOwnerModel);
        assertEquals(Arrays.asList(seedu.address.testutil.TypicalOwners.CARL,
                        seedu.address.testutil.TypicalOwners.ELLE,
                        seedu.address.testutil.TypicalOwners.FIONA),
                ownerModel.getFilteredOwnerList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code OwnerNameContainsKeywordsPredicate}.
     */
    private OwnerNameContainsKeywordsPredicate prepareOwnerPredicate(String userInput) {
        return new OwnerNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

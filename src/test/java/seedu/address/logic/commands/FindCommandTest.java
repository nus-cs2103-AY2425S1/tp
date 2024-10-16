package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.JAVIER;
import static seedu.address.testutil.TypicalPersons.KELLY;
import static seedu.address.testutil.TypicalPersons.LENOR;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithTags;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithTags(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBookWithTags(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        RoleContainsKeywordsPredicate firstRolePredicate =
                new RoleContainsKeywordsPredicate(Collections.singletonList("first"));

        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        RoleContainsKeywordsPredicate secondRolePredicate =
                new RoleContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstNamePredicate, firstRolePredicate);
        FindCommand findSecondCommand = new FindCommand(secondNamePredicate, secondRolePredicate);
        FindCommand findThirdCommand = new FindCommand(firstNamePredicate, secondRolePredicate);
        FindCommand findFourthCommand = new FindCommand(secondNamePredicate, firstRolePredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstNamePredicate, firstRolePredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different name and role predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // different name predicate only -> returns false
        assertFalse(findFirstCommand.equals(findFourthCommand));

        // different role predicate only -> returns false
        assertFalse(findFirstCommand.equals(findThirdCommand));
    }

    @Test
    public void execute_zeroNameAndRoleKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(" ");
        RoleContainsKeywordsPredicate rolePredicate = prepareRolePredicate(" ");
        FindCommand command = new FindCommand(namePredicate, rolePredicate);
        expectedModel.updateFilteredPersonList(namePredicate.or(rolePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleNameKeywordsOnly_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz Elle Kunz");
        RoleContainsKeywordsPredicate rolePredicate = prepareRolePredicate(" ");
        FindCommand command = new FindCommand(namePredicate, rolePredicate);
        expectedModel.updateFilteredPersonList(namePredicate.or(rolePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleRoleKeywordsOnly_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(" ");
        RoleContainsKeywordsPredicate rolePredicate = prepareRolePredicate("friend");
        FindCommand command = new FindCommand(namePredicate, rolePredicate);
        expectedModel.updateFilteredPersonList(namePredicate.or(rolePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JAVIER, LENOR), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleRoleAndRoleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("javier");
        RoleContainsKeywordsPredicate rolePredicate = prepareRolePredicate("relative");
        FindCommand command = new FindCommand(namePredicate, rolePredicate);
        expectedModel.updateFilteredPersonList(namePredicate.or(rolePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JAVIER, KELLY, LENOR), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        RoleContainsKeywordsPredicate rolePredicate = new RoleContainsKeywordsPredicate(Arrays.asList("friend"));
        FindCommand findCommand = new FindCommand(namePredicate, rolePredicate);
        String expected = FindCommand.class.getCanonicalName() + "{namePredicate=" + namePredicate
                + ", rolePredicate=" + rolePredicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code RoleContainsKeywordsPredicate}.
     */
    private RoleContainsKeywordsPredicate prepareRolePredicate(String userInput) {
        return new RoleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

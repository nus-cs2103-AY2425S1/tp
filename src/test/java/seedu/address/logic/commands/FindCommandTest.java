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
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PredicateGroup;
import seedu.address.testutil.FindUtil;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PredicateGroup firstPredicateGroup = FindUtil.getPredicateGroup(new NameContainsKeywordsPredicate(
                Set.of("first")));
        PredicateGroup secondPredicateGroup = FindUtil.getPredicateGroup(new NameContainsKeywordsPredicate(
                Set.of("second")));

        FindCommand findFirstCommand = new FindCommand(firstPredicateGroup);
        FindCommand findSecondCommand = new FindCommand(secondPredicateGroup);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicateGroup);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate group -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PredicateGroup predicateGroup = FindUtil.getPredicateGroup(prepareNamePredicate(" "));
        FindCommand command = new FindCommand(predicateGroup);
        expectedModel.updateFilteredPersonList(predicateGroup);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PredicateGroup predicateGroup = FindUtil.getPredicateGroup(prepareNamePredicate("Kurz Elle Kunz"));
        FindCommand command = new FindCommand(predicateGroup);
        expectedModel.updateFilteredPersonList(predicateGroup);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PredicateGroup predicateGroup = FindUtil.getPredicateGroup(
                new NameContainsKeywordsPredicate(Set.of("keyword")));
        FindCommand findCommand = new FindCommand(predicateGroup);
        String expected = FindCommand.class.getCanonicalName() + "{predicates=" + predicateGroup + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Set.of(userInput.split("\\s+")));
    }
}

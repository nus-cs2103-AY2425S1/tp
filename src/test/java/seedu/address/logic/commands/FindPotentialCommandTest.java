package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POTENTIAL_HIRES;
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
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PredicateContainer;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEmployeeCommand}.
 */
public class FindPotentialCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate1 =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        PredicateContainer predicateContainer1 =
                new PredicateContainer().addNameContainsKeywordsPredicate(nameContainsKeywordsPredicate1);
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate2 =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        PredicateContainer predicateContainer2 =
                new PredicateContainer().addNameContainsKeywordsPredicate(nameContainsKeywordsPredicate2);

        FindPotentialCommand findFirstCommand = new FindPotentialCommand(predicateContainer1);
        FindPotentialCommand findSecondCommand = new FindPotentialCommand(predicateContainer2);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPotentialCommand findFirstCommandCopy = new FindPotentialCommand(predicateContainer1);
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
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        PredicateContainer predicateContainer = new PredicateContainer().addNameContainsKeywordsPredicate(predicate);
        FindPotentialCommand command = new FindPotentialCommand(predicateContainer);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_POTENTIAL_HIRES.and(predicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        PredicateContainer predicateContainer = new PredicateContainer().addNameContainsKeywordsPredicate(predicate);
        FindPotentialCommand command = new FindPotentialCommand(predicateContainer);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_POTENTIAL_HIRES.and(predicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        PredicateContainer predicateContainer = new PredicateContainer().addNameContainsKeywordsPredicate(predicate);
        FindPotentialCommand findCommand = new FindPotentialCommand(predicateContainer);
        String expected = FindPotentialCommand.class.getCanonicalName() + "{predicate=" + predicateContainer + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EMPLOYEES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POTENTIAL_HIRES;
import static seedu.address.testutil.TypicalPersons.JEVAN;
import static seedu.address.testutil.TypicalPersons.KEVIN;
import static seedu.address.testutil.TypicalPersons.LILY;
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
public class FindEmployeeCommandTest {
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

        FindEmployeeCommand findFirstCommand = new FindEmployeeCommand(predicateContainer1);
        FindEmployeeCommand findSecondCommand = new FindEmployeeCommand(predicateContainer2);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindEmployeeCommand(predicateContainer1);
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
        FindEmployeeCommand command = new FindEmployeeCommand(predicateContainer);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_POTENTIAL_HIRES.and(predicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Jevan Lee Lily");
        PredicateContainer predicateContainer = new PredicateContainer().addNameContainsKeywordsPredicate(predicate);
        FindEmployeeCommand command = new FindEmployeeCommand(predicateContainer);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_EMPLOYEES.and(predicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JEVAN, KEVIN, LILY), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        PredicateContainer predicateContainer = new PredicateContainer().addNameContainsKeywordsPredicate(predicate);
        FindEmployeeCommand findCommand = new FindEmployeeCommand(predicateContainer);
        String expected = FindEmployeeCommand.class.getCanonicalName() + "{predicate=" + predicateContainer + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

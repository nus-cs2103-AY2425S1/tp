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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicates.IncomePredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PriorityPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private List<String> nameKeywords = new ArrayList<>();
    private List<String> addressKeywords = new ArrayList<>();
    private List<String> priorities = new ArrayList<>();
    private List<String> incomes = new ArrayList<>();

    @Test
    public void equals() {
        List<String> firstNameList = Arrays.asList("first");
        List<String> secondNameList = Arrays.asList("second");

        FindCommand findFirstCommand = new FindCommand(firstNameList, addressKeywords, priorities, incomes);
        FindCommand findSecondCommand = new FindCommand(secondNameList, addressKeywords, priorities, incomes);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstNameList, addressKeywords, priorities, incomes);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different filters -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_multipleKeywordsWithNamePrefix_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        nameKeywords = Arrays.asList("Carl", "Elle", "Fiona");

        FindCommand command = new FindCommand(nameKeywords, addressKeywords, priorities, incomes);
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(nameKeywords));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsWithAddressPrefix_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        addressKeywords = Arrays.asList("Clementi", "Jurong");

        FindCommand command = new FindCommand(nameKeywords, addressKeywords, priorities, incomes);
        expectedModel.updateFilteredPersonList(new AddressContainsKeywordsPredicate(addressKeywords));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiplePriorities_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 6);
        priorities = Arrays.asList("LOW", "HIGH");

        FindCommand command = new FindCommand(nameKeywords, addressKeywords, priorities, incomes);
        expectedModel.updateFilteredPersonList(new PriorityPredicate(priorities));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleIncomes_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        incomes = Arrays.asList("1000.00", "800");

        FindCommand command = new FindCommand(nameKeywords, addressKeywords, priorities, incomes);
        expectedModel.updateFilteredPersonList(new IncomePredicate(incomes));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ALICE, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleParameters_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        nameKeywords = Arrays.asList("A", "B", "C", "D");
        addressKeywords = Arrays.asList("Jurong", "Clementi", "wall street");
        priorities = Arrays.asList("MEDIUM", "HIGH");
        incomes = Arrays.asList("500.00", "0");

        FindCommand command = new FindCommand(nameKeywords, addressKeywords, priorities, incomes);
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(nameKeywords);
        AddressContainsKeywordsPredicate addressPredicate = new AddressContainsKeywordsPredicate(addressKeywords);
        PriorityPredicate priorityPredicate = new PriorityPredicate(priorities);
        IncomePredicate incomePredicate = new IncomePredicate(incomes);

        expectedModel.updateFilteredPersonList(
                namePredicate.and(addressPredicate).and(priorityPredicate).and(incomePredicate));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_noMatches_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        nameKeywords = Arrays.asList("Me");

        FindCommand command = new FindCommand(nameKeywords, addressKeywords, priorities, incomes);
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(nameKeywords));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        List<String> nameKeywords = Arrays.asList("keyword");
        FindCommand findCommand = new FindCommand(nameKeywords, addressKeywords, priorities, incomes);
        String expected = FindCommand.class.getCanonicalName() + "{names=" + nameKeywords
                + ", addresses=" + addressKeywords + ", priorities=" + priorities + ", incomes=" + incomes
                + "}";
        assertEquals(expected, findCommand.toString());
    }
}

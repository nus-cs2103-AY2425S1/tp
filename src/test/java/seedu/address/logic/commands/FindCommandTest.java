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
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PriorityPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new ArrayList<>(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new ArrayList<>(), new UserPrefs());

    private List<String> nameKeywords = new ArrayList<>();
    private List<String> addressKeywords = new ArrayList<>();
    private List<String> priorities = new ArrayList<>();

    @Test
    public void equals() {
        List<String> firstNameList = Arrays.asList("first");
        List<String> secondNameList = Arrays.asList("second");

        FindCommand findFirstCommand = new FindCommand(firstNameList, addressKeywords, priorities);
        FindCommand findSecondCommand = new FindCommand(secondNameList, addressKeywords, priorities);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstNameList, addressKeywords, priorities);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_multipleKeywordsWithNamePrefix_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        nameKeywords = Arrays.asList("Carl", "Elle", "Fiona");

        FindCommand command = new FindCommand(nameKeywords, addressKeywords, priorities);
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(nameKeywords));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsWithAddressPrefix_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        addressKeywords = Arrays.asList("Clementi", "Jurong");

        FindCommand command = new FindCommand(nameKeywords, addressKeywords, priorities);
        expectedModel.updateFilteredPersonList(new AddressContainsKeywordsPredicate(addressKeywords));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiplePriorities_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        priorities = Arrays.asList("LOW");

        FindCommand command = new FindCommand(nameKeywords, addressKeywords, priorities);
        expectedModel.updateFilteredPersonList(new PriorityPredicate(priorities));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleParameters_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        nameKeywords = Arrays.asList("A", "B", "C", "D");
        addressKeywords = Arrays.asList("Jurong", "Clementi", "wall street");
        priorities = Arrays.asList("MEDIUM", "HIGH");

        FindCommand command = new FindCommand(nameKeywords, addressKeywords, priorities);
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(nameKeywords);
        AddressContainsKeywordsPredicate addressPredicate = new AddressContainsKeywordsPredicate(addressKeywords);
        PriorityPredicate priorityPredicate = new PriorityPredicate(priorities);

        expectedModel.updateFilteredPersonList(namePredicate.and(addressPredicate).and(priorityPredicate));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_noMatches_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        nameKeywords = Arrays.asList("Me");

        FindCommand command = new FindCommand(nameKeywords, addressKeywords, priorities);
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(nameKeywords));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        List<String> nameKeywords = Arrays.asList("keyword");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(nameKeywords);
        FindCommand findCommand = new FindCommand(nameKeywords, addressKeywords, priorities);
        String expected = FindCommand.class.getCanonicalName() + "{names=" + nameKeywords
                + ", addresses=" + addressKeywords + ", priorities=" + priorities + "}";
        assertEquals(expected, findCommand.toString());
    }
}

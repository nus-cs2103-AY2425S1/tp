package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_FOUND_INTEREST;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_PERSON_FOUND_INTEREST;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.InterestContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindByInterestCommand}.
 */
public class FindByInterestCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        List<List<String>> firstAndKeywords = Collections.singletonList(Arrays.asList("reading"));
        List<String> firstOrKeywords = Arrays.asList("sports");
        InterestContainsKeywordsPredicate firstPredicate = new InterestContainsKeywordsPredicate(firstAndKeywords,
                firstOrKeywords);

        List<List<String>> secondAndKeywords = Collections.singletonList(Arrays.asList("writing"));
        List<String> secondOrKeywords = Arrays.asList("cooking");
        InterestContainsKeywordsPredicate secondPredicate = new InterestContainsKeywordsPredicate(secondAndKeywords,
                secondOrKeywords);

        FindByInterestCommand findFirstCommand = new FindByInterestCommand(firstPredicate);
        FindByInterestCommand findSecondCommand = new FindByInterestCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindByInterestCommand findFirstCommandCopy = new FindByInterestCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        InterestContainsKeywordsPredicate predicate = new InterestContainsKeywordsPredicate(Collections.emptyList(),
                Collections.emptyList());
        FindByInterestCommand command = new FindByInterestCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleOrKeyword_onePersonFound() {
        // Assume ALICE has the interest "reading"
        String expectedMessage = MESSAGE_PERSON_FOUND_INTEREST;
        InterestContainsKeywordsPredicate predicate = new InterestContainsKeywordsPredicate(Collections.emptyList(),
                Arrays.asList("reading"));
        FindByInterestCommand command = new FindByInterestCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleOrKeywords_multiplePersonsFound() {
        // Assume ALICE has "reading" and BOB has "sports"
        String expectedMessage = String.format(MESSAGE_PERSONS_FOUND_INTEREST, 2);
        InterestContainsKeywordsPredicate predicate = new InterestContainsKeywordsPredicate(Collections.emptyList(),
                Arrays.asList("reading", "football"));
        FindByInterestCommand command = new FindByInterestCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_andKeywords_singlePersonFound() {
        // Expect "Found 1 person that have similar interest" for exactly one result with AND keywords
        String expectedMessage = MESSAGE_PERSON_FOUND_INTEREST;
        List<List<String>> andKeywords = Collections.singletonList(Arrays.asList("reading", "swimming"));
        InterestContainsKeywordsPredicate predicate = new InterestContainsKeywordsPredicate(andKeywords,
                Collections.emptyList());
        FindByInterestCommand command = new FindByInterestCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Assuming ALICE has both "reading" and "music" as interests
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        List<List<String>> andKeywords = Collections.singletonList(Arrays.asList("reading"));
        List<String> orKeywords = Arrays.asList("sports");
        InterestContainsKeywordsPredicate predicate = new InterestContainsKeywordsPredicate(andKeywords, orKeywords);

        FindByInterestCommand findCommand = new FindByInterestCommand(predicate);
        String expected = FindByInterestCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }
}

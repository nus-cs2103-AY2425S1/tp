package careconnect.logic.commands;

import static careconnect.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static careconnect.logic.commands.CommandTestUtil.assertCommandSuccess;
import static careconnect.testutil.TypicalPersons.ALICE;
import static careconnect.testutil.TypicalPersons.CARL;
import static careconnect.testutil.TypicalPersons.ELLE;
import static careconnect.testutil.TypicalPersons.FIONA;
import static careconnect.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import careconnect.model.Model;
import careconnect.model.ModelManager;
import careconnect.model.UserPrefs;
import careconnect.model.person.NameAndAddressAndTagsContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameAndAddressAndTagsContainsKeywordsPredicate firstPredicate =
                new NameAndAddressAndTagsContainsKeywordsPredicate(
                        Collections.singletonList("first"),
                        Collections.singletonList("first"),
                        Collections.singletonList("first"));
        NameAndAddressAndTagsContainsKeywordsPredicate secondPredicate =
                new NameAndAddressAndTagsContainsKeywordsPredicate(
                        Collections.singletonList("second"),
                        Collections.singletonList("second"),
                        Collections.singletonList("second"));

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
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameAndAddressAndTagsContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz", "", "");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_addressKeyword_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameAndAddressAndTagsContainsKeywordsPredicate predicate = preparePredicate("", "jurong", "");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndAddressKeywords_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameAndAddressAndTagsContainsKeywordsPredicate predicate = preparePredicate("Alice", "jurong", "");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameAndAddressAndTagsContainsKeywordsPredicate predicate =
                new NameAndAddressAndTagsContainsKeywordsPredicate(
                        Arrays.asList("keyword"), Arrays.asList("keyword 2"), Arrays.asList("tagKeyword")
                        );
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameAndAddressAndTagsContainsKeywordsPredicate preparePredicate(
            String userInputName, String userInputAddress, String userInputTags) {
        return new NameAndAddressAndTagsContainsKeywordsPredicate(
                userInputName.length() == 0
                        ? Arrays.asList(new String[] {})
                        : Arrays.asList(userInputName.split("\\s+")),
                userInputAddress.length() == 0
                        ? Arrays.asList(new String[] {})
                        : Arrays.asList(userInputAddress.split("\\s+")),
                userInputTags.length() == 0
                        ? Arrays.asList(new String[] {})
                        : Arrays.asList(userInputAddress.split("\\s+"))
        );
    }
}

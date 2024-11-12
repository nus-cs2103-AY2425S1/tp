package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
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
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        TagContainsKeywordsPredicate firstTagPredicate =
                new TagContainsKeywordsPredicate(Set.of(new Tag("friend")));
        TagContainsKeywordsPredicate secondTagPredicate =
                new TagContainsKeywordsPredicate(Set.of(new Tag("colleague")));

        FindCommand findFirstCommand = new FindCommand(firstNamePredicate, firstTagPredicate);
        FindCommand findSecondCommand = new FindCommand(secondNamePredicate, secondTagPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstNamePredicate, firstTagPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        FindCommand findFirstNameSecondTag = new FindCommand(firstNamePredicate, secondTagPredicate);

        //different tag Predicate -> returns false
        assertFalse(findFirstCommand.equals(findFirstNameSecondTag));

        //different name Predicate -> returns false
        assertFalse(findSecondCommand.equals(findFirstNameSecondTag));

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
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(" ");
        TagContainsKeywordsPredicate tagPredicate = prepareTagPredicate(Set.of());
        FindCommand command = new FindCommand(namePredicate, tagPredicate);
        expectedModel.updateFilteredPersonList(namePredicate.or(tagPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz Elle Kunz");
        TagContainsKeywordsPredicate tagPredicate = prepareTagPredicate(Set.of(new Tag("owesMoney")));
        FindCommand command = new FindCommand(namePredicate, tagPredicate);
        expectedModel.updateFilteredPersonList(namePredicate.or(tagPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        TagContainsKeywordsPredicate tagPredicate = new TagContainsKeywordsPredicate(Set.of(new Tag("keyTag")));

        FindCommand findCommand = new FindCommand(namePredicate, tagPredicate);
        String expected = FindCommand.class.getCanonicalName()
                + "{predicates=" + " " + namePredicate + ", " + tagPredicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userNameInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userNameInput.split("\\s+")));
    }

    /**
     * Parses {@code userTagInput} into a {@code TagContainsKeywordsPredicate}.
     */
    private TagContainsKeywordsPredicate prepareTagPredicate(Set<Tag> userTagInput) {
        return new TagContainsKeywordsPredicate(userTagInput);
    }
}

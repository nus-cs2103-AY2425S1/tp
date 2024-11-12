package tahub.contacts.logic.commands.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tahub.contacts.testutil.TypicalPersons.CARL;
import static tahub.contacts.testutil.TypicalPersons.ELLE;
import static tahub.contacts.testutil.TypicalPersons.FIONA;
import static tahub.contacts.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import tahub.contacts.model.Model;
import tahub.contacts.model.ModelManager;
import tahub.contacts.model.UserPrefs;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.NameContainsKeywordsPredicate;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class PersonFindCommandTest {
    private Model model =
            new ModelManager(
                    getTypicalAddressBook(),
                    new UserPrefs(),
                    new UniqueCourseList(),
                    new StudentCourseAssociationList());
    private Model expectedModel =
            new ModelManager(getTypicalAddressBook(), new UserPrefs(), model.getCourseList(), model.getScaList());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        PersonFindCommand findFirstCommand = new PersonFindCommand(firstPredicate);
        PersonFindCommand findSecondCommand = new PersonFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        PersonFindCommand findFirstCommandCopy = new PersonFindCommand(firstPredicate);
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
        PersonFindCommand command = new PersonFindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        PersonFindCommand command = new PersonFindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        PersonFindCommand personFindCommand = new PersonFindCommand(predicate);
        String expected = PersonFindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, personFindCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

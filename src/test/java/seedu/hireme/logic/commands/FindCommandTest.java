package seedu.hireme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.logic.Messages.MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW;
import static seedu.hireme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hireme.testutil.TypicalInternshipApplications.APPLE;
import static seedu.hireme.testutil.TypicalInternshipApplications.BOFA;
import static seedu.hireme.testutil.TypicalInternshipApplications.CITIBANK;
import static seedu.hireme.testutil.TypicalInternshipApplications.DELL;
import static seedu.hireme.testutil.TypicalInternshipApplications.EY;
import static seedu.hireme.testutil.TypicalInternshipApplications.FIGMA;
import static seedu.hireme.testutil.TypicalInternshipApplications.GOOGLE;
import static seedu.hireme.testutil.TypicalInternshipApplications.YAHOO;
import static seedu.hireme.testutil.TypicalInternshipApplications.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.hireme.model.Model;
import seedu.hireme.model.ModelManager;
import seedu.hireme.model.UserPrefs;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */

// Todo add more test cases to deal with prefix predicates
public class FindCommandTest {
    private Model<InternshipApplication> model = new ModelManager<>(getTypicalAddressBook(), new UserPrefs());
    private Model<InternshipApplication> expectedModel = new ModelManager<>(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

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
    public void execute_noInternshipApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate =
                preparePredicate("ThisIsSuchAUniqueStringSuchThatNoOtherCompanyNameShouldBeTheSameAsThis");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredList());
    }

    @Test
    public void execute_multipleKeywords_multipleInternshipApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate predicate = preparePredicate("Apple Yahoo");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(APPLE, YAHOO), model.getFilteredList());
    }

    @Test
    public void execute_keywordsInUpperCase_validInternshipApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("APPLE");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(APPLE), model.getFilteredList());
    }

    @Test
    public void execute_keywordsInLowerCase_validInternshipApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("apple");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(APPLE), model.getFilteredList());
    }

    @Test
    public void execute_entireAlphabetAsKeywords_allInternshipApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW, 7);
        NameContainsKeywordsPredicate predicate =
                preparePredicate("a b c d e f g h i j k l m n o p q r s t u v w x y z");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(APPLE, BOFA, CITIBANK, DELL, EY, FIGMA, YAHOO), model.getFilteredList());
    }

    @Test
    public void execute_commonKeyword_multipleInternshipApplicationsFound() {
        Model<InternshipApplication> modelWithGoogleAndYahoo = new ModelManager<>(getTypicalAddressBook(),
                                                                                  new UserPrefs());
        modelWithGoogleAndYahoo.addItem(GOOGLE);
        String expectedMessage = String.format(MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate predicate = preparePredicate("oo");
        FindCommand command = new FindCommand(predicate);
        Model<InternshipApplication> expectedModel = new ModelManager<>(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addItem(GOOGLE);
        expectedModel.updateFilteredList(predicate);
        assertCommandSuccess(command, modelWithGoogleAndYahoo, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(YAHOO, GOOGLE), modelWithGoogleAndYahoo.getFilteredList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

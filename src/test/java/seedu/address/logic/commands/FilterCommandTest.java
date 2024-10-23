package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.PredefinedAssignmentsData;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FilterCommandTest {
    private Model model = new ModelManager(
            getTypicalAddressBook(),
            new UserPrefs(),
            new PredefinedAssignmentsData());
    private Model expectedModel = new ModelManager(
            getTypicalAddressBook(),
            new UserPrefs(),
            new PredefinedAssignmentsData());

    @Test
    public void equals() {

        Tag firstTag = new Tag("Student");
        Tag secondTag = new Tag("Professor");

        Set<Tag> firstSet = new HashSet<>();
        firstSet.add(firstTag);

        Set<Tag> secondSet = new HashSet<>();
        secondSet.add(secondTag);

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstSet);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondSet);

        FilterCommand firstFilterCommand = new FilterCommand(firstPredicate);
        FilterCommand secondFilterCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstFilterCommand.equals(firstFilterCommand));

        // same values -> returns true
        FilterCommand firstFindCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(firstFilterCommand.equals(firstFindCommandCopy));

        // different types -> return false
        assertFalse(firstFilterCommand.equals(1));

        // null -> return false
        assertFalse(firstFilterCommand.equals(null));

        // different set -> return false
        assertFalse(firstFilterCommand.equals(secondFilterCommand));

    }

    @Test
    public void execute_singleTag_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        TagContainsKeywordsPredicate predicate = preparePredicate(new Tag("friends"));
        FilterCommand command = new FilterCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleTag_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        TagContainsKeywordsPredicate predicate = preparePredicate(new Tag(VALID_TAG_HUSBAND));
        FilterCommand command = new FilterCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleTag_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        TagContainsKeywordsPredicate predicate = preparePredicate(
                new Tag("owesMoney"),
                new Tag("friends"),
                new Tag(VALID_TAG_HUSBAND));
        FilterCommand command = new FilterCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleTag_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagContainsKeywordsPredicate predicate = preparePredicate(
                new Tag("unusedTag1"),
                new Tag("unusedTag2"));
        FilterCommand command = new FilterCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tagSet = new HashSet<>(Collections.singletonList(new Tag("keyword")));
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(tagSet);
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }


    private TagContainsKeywordsPredicate preparePredicate(Tag... tagArray) {
        Set<Tag> result = new HashSet<>(Arrays.asList(tagArray));
        return new TagContainsKeywordsPredicate(result);
    }
}

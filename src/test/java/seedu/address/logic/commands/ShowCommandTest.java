package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_NO_STUDENTS_FOUND;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.GroupContainsKeywordsPredicate;
import seedu.address.model.person.Person;

public class ShowCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Predicate<Person> firstPredicate = new GroupContainsKeywordsPredicate(Collections.singletonList("1"));
        Predicate<Person> secondPredicate = new GroupContainsKeywordsPredicate(Collections.singletonList("2"));

        ShowCommand showFirstCommand = new ShowCommand(firstPredicate);
        ShowCommand showSecondCommand = new ShowCommand(secondPredicate);

        // same object -> returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values -> returns true
        ShowCommand showFirstCommandCopy = new ShowCommand(firstPredicate);
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types -> returns false
        assertFalse(showFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }

    @Test
    public void execute_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_NO_STUDENTS_FOUND, 0);

        // Predicate to look for groups containing the string "50"
        // There are no students with groups containing the word "50"
        Predicate<Person> predicate = new GroupContainsKeywordsPredicate(Arrays.asList("50"));
        ShowCommand command = new ShowCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        //Message should say that there are no students found
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Collection should be empty as no students are found
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_groupKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        // Predicate to look for groups containing the string "2"
        // There are 2 students with groups containing the word "2"
        Predicate<Person> predicate = new GroupContainsKeywordsPredicate(Arrays.asList("1"));
        ShowCommand command = new ShowCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        // Message should say that two students are found
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Two students should be in the filtered list
        List<Person> expectedList = Arrays.asList(ALICE, BENSON);
        assertEquals(expectedList, model.getFilteredPersonList());
    }

    @Test
    public void execute_groupKeyword_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        // Predicate to look for groups containing the string "1"
        // There is 1 student with groups containing the word "1"
        Predicate<Person> predicate = new GroupContainsKeywordsPredicate(Arrays.asList("2"));
        ShowCommand command = new ShowCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        // Message should say that one student is found
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // One student should be in the filtered list
        List<Person> expectedList = Arrays.asList(CARL);
        assertEquals(expectedList, model.getFilteredPersonList());
    }

    @Test
    public void testHashCode_sameShowCommand_shouldHaveSameHashCode() {
        Predicate<Person> predicate = new GroupContainsKeywordsPredicate(Arrays.asList("2"));

        // Two Show Command objects with the same predicate
        ShowCommand first = new ShowCommand(predicate);
        ShowCommand second = new ShowCommand(predicate);

        // Assert that the hash codes are the same
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void testHashCode_differentShowCommand_shouldHaveDifferentHashCode() {
        Predicate<Person> predicateOne = new GroupContainsKeywordsPredicate(Arrays.asList("1"));
        Predicate<Person> predicateTwo = new GroupContainsKeywordsPredicate(Arrays.asList("2"));

        // Two Show Command objects with different predicates
        ShowCommand first = new ShowCommand(predicateOne);
        ShowCommand second = new ShowCommand(predicateTwo);

        // Assert that the hash codes are different
        assertNotEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void testToString() {
        Predicate<Person> predicate = new GroupContainsKeywordsPredicate(Arrays.asList("1"));
        ShowCommand showCommand = new ShowCommand(predicate);

        String expectedString = "seedu.address.logic.commands.ShowCommand{predicate=seedu.address"
                + ".model.group.GroupContainsKeywordsPredicate{keywords=[1]}}";
        String actualString = showCommand.toString();

        assertEquals(expectedString, actualString);
    }
}

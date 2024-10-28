package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalStudents.CARL;
import static seedu.address.testutil.TypicalStudents.DANIEL;
import static seedu.address.testutil.TypicalStudents.ELLE;
import static seedu.address.testutil.TypicalStudents.FIONA;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Days;
import seedu.address.model.student.predicates.AttributeContainsKeywordsPredicate;
import seedu.address.model.student.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.student.predicates.ScheduleContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private static NameContainsKeywordsPredicate emptyNamePredicate;
    private static NameContainsKeywordsPredicate namePredicate;
    private static ScheduleContainsKeywordsPredicate schedulePredicate;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @BeforeAll
    public static void setUp() {
        emptyNamePredicate = prepareNamePredicate(" ");
        namePredicate = prepareNamePredicate("Kurz Elle Kunz");
        schedulePredicate = prepareSchedulePredicate(Days.WEDNESDAY, Days.FRIDAY);
    }
    @Test
    public void equals() {
        List<AttributeContainsKeywordsPredicate<?>> firstPredicateList = List.of(namePredicate);
        List<AttributeContainsKeywordsPredicate<?>> secondPredicateList = List.of(schedulePredicate);

        FindCommand findFirstCommand = new FindCommand(firstPredicateList);
        FindCommand findSecondCommand = new FindCommand(secondPredicateList);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicateList);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroNameKeywords_noStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 0);

        FindCommand command = new FindCommand(List.of(emptyNamePredicate));
        expectedModel.updateFilteredStudentList(List.of(emptyNamePredicate));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());
    }

    @Test
    public void execute_multipleNameKeywords_multipleStudentsFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 3);


        FindCommand command = new FindCommand(List.of(namePredicate));
        expectedModel.updateFilteredStudentList(List.of(namePredicate));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredStudentList());
    }
    @Test
    public void execute_scheduleOnWednesday_oneStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);

        ScheduleContainsKeywordsPredicate schedulePredicate = prepareSchedulePredicate(Days.WEDNESDAY);

        FindCommand command = new FindCommand(List.of(schedulePredicate));
        expectedModel.updateFilteredStudentList(List.of(schedulePredicate));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(DANIEL), model.getFilteredStudentList());
    }

    @Test
    public void execute_oneNameWithMultipleScheduleKeywords_oneStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Daniel");
        FindCommand command = new FindCommand(List.of(namePredicate, schedulePredicate));
        expectedModel.updateFilteredStudentList(List.of(namePredicate, schedulePredicate));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(DANIEL), model.getFilteredStudentList());
    }

    @Test
    public void execute_multipleScheduleKeywords_multipleStudentsFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 2);

        FindCommand command = new FindCommand(List.of(schedulePredicate));
        expectedModel.updateFilteredStudentList(List.of(schedulePredicate));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, FIONA), model.getFilteredStudentList());
    }

    @Test
    public void execute_nameAndScheduleKeywords_noStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 0);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Elle");
        ScheduleContainsKeywordsPredicate schedulePredicate = prepareSchedulePredicate(Days.WEDNESDAY);

        FindCommand command = new FindCommand(List.of(namePredicate, schedulePredicate));
        expectedModel.updateFilteredStudentList(List.of(namePredicate, schedulePredicate));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());
    }

    @Test
    public void execute_nameAndScheduleKeywords_oneStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Elle");
        ScheduleContainsKeywordsPredicate schedulePredicate = prepareSchedulePredicate(Days.THURSDAY);

        FindCommand command = new FindCommand(List.of(namePredicate, schedulePredicate));
        expectedModel.updateFilteredStudentList(List.of(namePredicate, schedulePredicate));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ELLE), model.getFilteredStudentList());
    }

    @Test
    public void toStringMethod() {
        List<AttributeContainsKeywordsPredicate<?>> predicates = List.of(namePredicate, schedulePredicate);

        FindCommand findCommand = new FindCommand(predicates);
        String expected = FindCommand.class.getCanonicalName() + "{predicates=" + predicates + "}";
        assertEquals(expected, findCommand.toString());
    }

    @Test
    public void hashCodeMethod() {
        List<AttributeContainsKeywordsPredicate<?>> predicates = List.of(namePredicate, schedulePredicate);

        FindCommand findCommand = new FindCommand(predicates);
        int expected = Objects.hash(predicates);
        assertEquals(expected, findCommand.hashCode());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private static NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code days} into a {@code ScheduleContainsKeywordsPredicate}.
     */
    private static ScheduleContainsKeywordsPredicate prepareSchedulePredicate(Days... days) {
        return new ScheduleContainsKeywordsPredicate(Arrays.stream(days).toList());
    }
}

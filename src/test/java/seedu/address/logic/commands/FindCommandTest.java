package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_CS2030;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BENSON;
import static seedu.address.testutil.TypicalStudents.DANIEL;
import static seedu.address.testutil.TypicalStudents.ELLE;
import static seedu.address.testutil.TypicalStudents.GEORGE;
import static seedu.address.testutil.TypicalStudents.KEYWORD_MATCHING_BE_TWO_MATCH;
import static seedu.address.testutil.TypicalStudents.KEYWORD_MATCHING_ELLE_ONE_MATCH;
import static seedu.address.testutil.TypicalStudents.KEYWORD_MATCHING_MEIER_TWO_MATCH;
import static seedu.address.testutil.TypicalStudents.getTypicalStudentOnlyAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.IsStudentOfCoursePredicate;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalStudentOnlyAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalStudentOnlyAddressBook(), new UserPrefs());

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

        // different student -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywordsName_noStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());
    }

    @Test
    public void execute_zeroKeywordsCourse_noStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 0);
        IsStudentOfCoursePredicate predicate = prepareCoursePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());
    }

    @Test
    public void execute_oneNameKeyword_multipleStudentsFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 2);
        FindCommand command = new FindCommand(List.of(
                new NameContainsKeywordsPredicate(List.of(KEYWORD_MATCHING_MEIER_TWO_MATCH))
        ));
        expectedModel.updateFilteredStudentList(command.getPredicate());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredStudentList());
    }

    @Test
    public void execute_oneCourseKeyword_multipleStudentsFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 3);
        FindCommand command = new FindCommand(List.of(
                new IsStudentOfCoursePredicate(List.of(VALID_COURSE_CS2103T))
        ));
        expectedModel.updateFilteredStudentList(command.getPredicate());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredStudentList());
    }


    @Test
    public void execute_multipleNamesIntersection_oneStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindCommand command = new FindCommand(List.of(
                new NameContainsKeywordsPredicate(List.of(KEYWORD_MATCHING_MEIER_TWO_MATCH)),
                new NameContainsKeywordsPredicate(List.of(KEYWORD_MATCHING_BE_TWO_MATCH))
        ));
        expectedModel.updateFilteredStudentList(command.getPredicate());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredStudentList());
    }

    @Test
    public void execute_multipleCoursesIntersection_oneStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindCommand command = new FindCommand(List.of(
                new IsStudentOfCoursePredicate(List.of(VALID_COURSE_CS2103T)),
                new IsStudentOfCoursePredicate(List.of(VALID_COURSE_CS2101))
        ));
        expectedModel.updateFilteredStudentList(command.getPredicate());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredStudentList());
    }

    @Test
    public void execute_multipleNamesUnion_threeStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 3);
        FindCommand command = new FindCommand(List.of(
                new NameContainsKeywordsPredicate(List.of(KEYWORD_MATCHING_MEIER_TWO_MATCH,
                        KEYWORD_MATCHING_BE_TWO_MATCH))
        ));
        expectedModel.updateFilteredStudentList(command.getPredicate());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL, GEORGE), model.getFilteredStudentList());
    }

    @Test
    public void execute_multipleCoursesUnion_fourStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 4);
        FindCommand command = new FindCommand(List.of(
                new IsStudentOfCoursePredicate(List.of(VALID_COURSE_CS2103T,
                        VALID_COURSE_CS2101))
        ));
        expectedModel.updateFilteredStudentList(command.getPredicate());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, ELLE), model.getFilteredStudentList());
    }

    @Test
    public void execute_nameAndCourseIntersect_oneStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindCommand command = new FindCommand(List.of(
                new NameContainsKeywordsPredicate(List.of(KEYWORD_MATCHING_MEIER_TWO_MATCH)),
                new IsStudentOfCoursePredicate(List.of(VALID_COURSE_CS2101))
        ));
        expectedModel.updateFilteredStudentList(command.getPredicate());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredStudentList());
    }

    @Test
    public void execute_unionOfNameIntersectWithCourse_twoStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 2);
        FindCommand command = new FindCommand(List.of(
                new NameContainsKeywordsPredicate(List.of(KEYWORD_MATCHING_MEIER_TWO_MATCH,
                        KEYWORD_MATCHING_ELLE_ONE_MATCH)),
                new IsStudentOfCoursePredicate(List.of(VALID_COURSE_CS2101))
        ));
        expectedModel.updateFilteredStudentList(command.getPredicate());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ELLE), model.getFilteredStudentList());
    }

    @Test
    public void execute_unionOfNameIntersectWithUnionOfCourse_twoStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 2);
        FindCommand command = new FindCommand(List.of(
                new NameContainsKeywordsPredicate(List.of(KEYWORD_MATCHING_MEIER_TWO_MATCH,
                        KEYWORD_MATCHING_BE_TWO_MATCH)),
                new IsStudentOfCoursePredicate(List.of(VALID_COURSE_CS2101,
                        VALID_COURSE_CS2030))
        ));
        expectedModel.updateFilteredStudentList(command.getPredicate());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, GEORGE), model.getFilteredStudentList());
    }

    @Test
    public void toStringMethod() {
        List<Predicate<Student>> predicates = List.of(
                new NameContainsKeywordsPredicate(List.of("Alice")),
                new NameContainsKeywordsPredicate(List.of("Bob"))
        );
        FindCommand findCommand = new FindCommand(predicates);
        String expected = FindCommand.class.getCanonicalName() + "{predicates=" + predicates + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    private IsStudentOfCoursePredicate prepareCoursePredicate(String userInput) {
        return new IsStudentOfCoursePredicate(Arrays.asList(userInput.split("\\s+")));
    }

    @Test
    public void getCommandTypeMethod() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        FindCommand findCommand = new FindCommand(firstPredicate);
        assertEquals(findCommand.getCommandType(), CommandType.FINDSTUDENT);
    }
}

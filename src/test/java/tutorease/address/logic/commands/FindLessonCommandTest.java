package tutorease.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.logic.Messages.MESSAGE_LESSONS_LISTED_OVERVIEW;
import static tutorease.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorease.address.testutil.TypicalLessons.ART_LESSON;
import static tutorease.address.testutil.TypicalLessons.ENGLISH_LESSON;
import static tutorease.address.testutil.TypicalLessons.GEOGRAPHY_LESSON;
import static tutorease.address.testutil.TypicalLessons.HISTORY_LESSON;
import static tutorease.address.testutil.TypicalLessons.MATH_LESSON;
import static tutorease.address.testutil.TypicalLessons.MUSIC_LESSON;
import static tutorease.address.testutil.TypicalLessons.SCIENCE_LESSON;
import static tutorease.address.testutil.TypicalLessons.getTypicalLessons;
import static tutorease.address.testutil.TypicalStudents.getTypicalTutorEase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.Messages;
import tutorease.address.model.Model;
import tutorease.address.model.ModelManager;
import tutorease.address.model.UserPrefs;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.LessonContainsNamesPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindLessonCommand}.
 */
public class FindLessonCommandTest {
    private Model model = new ModelManager(getTypicalTutorEase(), new UserPrefs(), getTypicalLessons());
    private Model expectedModel = new ModelManager(getTypicalTutorEase(), new UserPrefs(), getTypicalLessons());

    @Test
    public void equals() {
        LessonContainsNamesPredicate firstPredicate =
                new LessonContainsNamesPredicate(Collections.singletonList("first"));
        LessonContainsNamesPredicate secondPredicate =
                new LessonContainsNamesPredicate(Collections.singletonList("second"));

        FindLessonCommand findLessonFirstCommand = new FindLessonCommand(firstPredicate);
        FindLessonCommand findLessonSecondCommand = new FindLessonCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findLessonFirstCommand.equals(findLessonFirstCommand));

        // same values -> returns true
        FindLessonCommand findLessonFirstCommandCopy = new FindLessonCommand(firstPredicate);
        assertTrue(findLessonFirstCommand.equals(findLessonFirstCommandCopy));

        // different types -> returns false
        assertFalse(findLessonFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findLessonFirstCommand.equals(null));

        // different lesson -> returns false
        assertFalse(findLessonFirstCommand.equals(findLessonSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noLessonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_NO_LESSONS_FOUND);
        LessonContainsNamesPredicate predicate = preparePredicate("test");
        FindLessonCommand command = new FindLessonCommand(predicate);
        expectedModel.updateFilteredLessonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredLessonList());
    }

    @Test
    public void execute_multipleKeywords_multipleLessonsFound() {
        String expectedMessage = String.format(MESSAGE_LESSONS_LISTED_OVERVIEW, 7);
        LessonContainsNamesPredicate predicate = preparePredicate("Alice Bob");
        FindLessonCommand command = new FindLessonCommand(predicate);
        expectedModel.updateFilteredLessonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        List<Lesson> expectedLessons = Arrays.asList(
                MATH_LESSON,
                SCIENCE_LESSON,
                ENGLISH_LESSON,
                HISTORY_LESSON,
                GEOGRAPHY_LESSON,
                ART_LESSON,
                MUSIC_LESSON);
        expectedLessons.sort(Lesson::compareTo);
        assertEquals(expectedLessons, model.getFilteredLessonList());
    }

    @Test
    public void execute_multipleKeywordsCaseInsensitive_multipleLessonsFound() {
        String expectedMessage = String.format(MESSAGE_LESSONS_LISTED_OVERVIEW, 7);
        LessonContainsNamesPredicate predicate = preparePredicate("aLiCe bOb");
        FindLessonCommand command = new FindLessonCommand(predicate);
        expectedModel.updateFilteredLessonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        List<Lesson> expectedLessons = Arrays.asList(
                MATH_LESSON,
                SCIENCE_LESSON,
                ENGLISH_LESSON,
                HISTORY_LESSON,
                GEOGRAPHY_LESSON,
                ART_LESSON,
                MUSIC_LESSON);
        expectedLessons.sort(Lesson::compareTo);
        assertEquals(expectedLessons, model.getFilteredLessonList());
    }

    @Test
    public void execute_oneKeyword_multipleOfCorrectLessonsFound() {
        String expectedMessage = String.format(MESSAGE_LESSONS_LISTED_OVERVIEW, 4);
        LessonContainsNamesPredicate predicate = preparePredicate("Alice");
        FindLessonCommand command = new FindLessonCommand(predicate);
        expectedModel.updateFilteredLessonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        List<Lesson> expectedLessons = Arrays.asList(
                MATH_LESSON,
                SCIENCE_LESSON,
                HISTORY_LESSON,
                GEOGRAPHY_LESSON);
        expectedLessons.sort(Lesson::compareTo);
        assertEquals(expectedLessons, model.getFilteredLessonList());
    }

    @Test
    public void execute_incompleteKeyword_noLessonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_NO_LESSONS_FOUND);
        LessonContainsNamesPredicate predicate = preparePredicate("Al");
        FindLessonCommand command = new FindLessonCommand(predicate);
        expectedModel.updateFilteredLessonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredLessonList());
    }

    @Test
    public void toStringMethod() {
        LessonContainsNamesPredicate predicate = new LessonContainsNamesPredicate(Arrays.asList(
                "keyword"));
        FindLessonCommand findLessonCommand = new FindLessonCommand(predicate);
        String expected = FindLessonCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findLessonCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code LessonContainsNamesPredicate}.
     */
    private LessonContainsNamesPredicate preparePredicate(String userInput) {
        return new LessonContainsNamesPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

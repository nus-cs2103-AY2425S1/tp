package tutorease.address.logic.commands;
import static tutorease.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorease.address.testutil.TypicalLessons.getTypicalLessons;
import static tutorease.address.testutil.TypicalStudents.getTypicalTutorEase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutorease.address.model.LessonSchedule;
import tutorease.address.model.Model;
import tutorease.address.model.ModelManager;
import tutorease.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListLessonCommand.
 */
public class ListLessonCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorEase(), new UserPrefs(), getTypicalLessons());
        expectedModel = new ModelManager(model.getTutorEase(), new UserPrefs(), getTypicalLessons());
    }

    @Test
    public void execute_noLessons_found() {
        model.updateFilteredLessonList(lesson -> false); // No lessons to show
        expectedModel.updateFilteredLessonList(lesson -> false); // Ensure expected model matches

        assertCommandSuccess(new ListLessonCommand(), model, ListLessonCommand.MESSAGE_NO_LESSONS_FOUND, expectedModel);
    }

    @Test
    public void execute_multipleLessons_found() {
        model.updateFilteredLessonList(lesson -> true); // Show all lessons
        expectedModel.updateFilteredLessonList(lesson -> true); // Ensure expected model matches

        assertCommandSuccess(new ListLessonCommand(), model, ListLessonCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyFilteredList() {
        model.updateFilteredLessonList(lesson -> lesson.getAddress().equals("3")); // No lessons match
        expectedModel.updateFilteredLessonList(lesson -> lesson.getAddress().equals("3"));

        assertCommandSuccess(new ListLessonCommand(), model, ListLessonCommand.MESSAGE_NO_LESSONS_FOUND, expectedModel);
    }

    @Test
    public void execute_emptyLessonList() {
        model = new ModelManager(getTypicalTutorEase(), new UserPrefs(), new LessonSchedule());
        expectedModel = new ModelManager(model.getTutorEase(), new UserPrefs(), new LessonSchedule());

        assertCommandSuccess(new ListLessonCommand(), model, ListLessonCommand.MESSAGE_NO_LESSONS_FOUND, expectedModel);
    }

}

package tutorease.address.logic.commands;

import static tutorease.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorease.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorease.address.testutil.TypicalStudents.getTypicalTutorEase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutorease.address.commons.core.index.Index;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.LessonSchedule;
import tutorease.address.model.Model;
import tutorease.address.model.ModelManager;
import tutorease.address.model.UserPrefs;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.StudentId;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.LessonBuilder;
import tutorease.address.testutil.TypicalStudents;

public class DeleteLessonCommandIntegrationTest {
    private Model model;
    private Person validPerson = TypicalStudents.ALICE;
    private final StudentId studentId = new StudentId("1");
    private final String startDateTime = "10-11-2024 02:18";
    private final String endDateTime = "10-11-2024 03:18";

    public DeleteLessonCommandIntegrationTest() throws ParseException {
    }

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorEase(), new UserPrefs(), new LessonSchedule());
        validPerson = model.getTutorEase().getPersonList().get(0);
    }

    @Test
    public void execute_deleteLesson_success() throws ParseException {
        Model expectedModel = new ModelManager(model.getTutorEase(), new UserPrefs(), model.getLessonSchedule());
        Lesson validLesson = new LessonBuilder().withName(validPerson)
                .withStartDateTime(startDateTime)
                .withEndDateTime(endDateTime)
                .build();
        expectedModel.addLesson(validLesson);

        assertCommandSuccess(new AddLessonCommand(studentId, validLesson.getStartDateTime(),
                        validLesson.getEndDateTime()), model,
                String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                expectedModel);

        assertCommandSuccess(new DeleteLessonCommand(Index.fromOneBased(1)), model,
                String.format(DeleteLessonCommand.MESSAGE_SUCCESS, validLesson),
                expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws ParseException {
        Model expectedModel = new ModelManager(model.getTutorEase(), new UserPrefs(), model.getLessonSchedule());
        Lesson validLesson = new LessonBuilder().withName(validPerson)
                .withStartDateTime(startDateTime)
                .withEndDateTime(endDateTime)
                .build();
        expectedModel.addLesson(validLesson);

        assertCommandSuccess(new AddLessonCommand(studentId, validLesson.getStartDateTime(),
                        validLesson.getEndDateTime()), model,
                String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                expectedModel);

        assertCommandFailure(new DeleteLessonCommand(Index.fromOneBased(10)), model,
                DeleteLessonCommand.MESSAGE_INVALID_INDEX);
    }
}

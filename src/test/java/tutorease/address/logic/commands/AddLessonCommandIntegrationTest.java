package tutorease.address.logic.commands;

import static tutorease.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorease.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorease.address.testutil.TypicalLessons.getTypicalLessons;
import static tutorease.address.testutil.TypicalStudents.getTypicalTutorEase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.Model;
import tutorease.address.model.ModelManager;
import tutorease.address.model.UserPrefs;
import tutorease.address.model.lesson.EndDateTime;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.StartDateTime;
import tutorease.address.model.lesson.StudentId;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.LessonBuilder;
import tutorease.address.testutil.TypicalStudents;

public class AddLessonCommandIntegrationTest {
    private Model model;
    private Person validPerson = TypicalStudents.ALICE;
    private final StudentId studentId = new StudentId("1");
    private final String startDateTime = "10-11-2024 02:18";
    private final String endDateTime = "10-11-2024 03:18";

    public AddLessonCommandIntegrationTest() throws ParseException {
    }

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorEase(), new UserPrefs(), getTypicalLessons());
        validPerson = model.getTutorEase().getPersonList().get(0);
    }

    @Test
    public void execute_newLesson_success() throws ParseException {
        Model expectedModel = new ModelManager(model.getTutorEase(), new UserPrefs(), model.getLessonSchedule());
        Lesson validLesson = new LessonBuilder().withName(validPerson)
                .withStartDateTime(startDateTime)
                .withEndDateTime(endDateTime)
                .build();
        expectedModel.addLesson(validLesson);

        assertCommandSuccess(new AddLessonCommand(studentId, validLesson.getFee(), validLesson.getStartDateTime(),
                        validLesson.getEndDateTime()), model,
                String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                expectedModel);
    }
    @Test
    public void execute_duplicateLesson_throwsCommandException() throws ParseException {
        StartDateTime sdt = StartDateTime.createStartDateTime(startDateTime);
        EndDateTime edt = EndDateTime.createEndDateTime(startDateTime);
        Lesson overlap = new LessonBuilder().withName(validPerson)
                .withStartDateTime(startDateTime)
                .withEndDateTime(endDateTime)
                .build();
        model.addLesson(overlap);
        assertCommandFailure(new AddLessonCommand(studentId, overlap.getFee(), sdt, edt), model,
                AddLessonCommand.MESSAGE_OVERLAP_LESSON);
    }
}

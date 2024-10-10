package tutorease.address.logic.commands;

import static tutorease.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorease.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorease.address.testutil.TypicalLessons.getTypicalLessons;
import static tutorease.address.testutil.TypicalPersons.getTypicalTutorEase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.Model;
import tutorease.address.model.ModelManager;
import tutorease.address.model.UserPrefs;
import tutorease.address.model.lesson.EndDateTime;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.LocationIndex;
import tutorease.address.model.lesson.StartDateTime;
import tutorease.address.model.lesson.StudentId;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.LessonBuilder;
import tutorease.address.testutil.TypicalPersons;

public class AddLessonCommandIntegrationTest {
    private Model model;
    private Person validPerson = TypicalPersons.ALICE;
    private StudentId studentId = new StudentId("1");
    private LocationIndex locationIndex = new LocationIndex("1");
    private StartDateTime startDateTime = StartDateTime.createStartDateTime("10-11-2024 02:18");
    private EndDateTime endDateTime = EndDateTime.createEndDateTime("10-11-2024 03:18");

    public AddLessonCommandIntegrationTest() throws ParseException {
    }

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorEase(), new UserPrefs(), getTypicalLessons());
    }

    @Test
    public void execute_newLesson_success() throws ParseException {
        Model expectedModel = new ModelManager(model.getTutorEase(), new UserPrefs(), model.getLessonSchedule());
        Lesson validLesson = new LessonBuilder().withName(validPerson).build();
        expectedModel.addLesson(validLesson);

        assertCommandSuccess(new AddLessonCommand(studentId, startDateTime, locationIndex, endDateTime), model,
                String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                expectedModel);
    }
    @Test
    public void execute_duplicateLesson_throwsCommandException() throws ParseException {
        model.addLesson(new LessonBuilder().withName(validPerson).build());
        assertCommandFailure(new AddLessonCommand(studentId, startDateTime, locationIndex, endDateTime), model,
                AddLessonCommand.MESSAGE_OVERLAP_LESSON);
    }
}

package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLessons.getTypicalLessons;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.EndDateTime;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LocationIndex;
import seedu.address.model.lesson.StartDateTime;
import seedu.address.model.lesson.StudentId;
import seedu.address.model.person.Person;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.TypicalPersons;

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
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalLessons());
    }

    @Test
    public void execute_newLesson_success() throws ParseException {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getLessonSchedule());
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

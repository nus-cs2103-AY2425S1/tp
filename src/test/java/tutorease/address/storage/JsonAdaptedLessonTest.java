package tutorease.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorease.address.testutil.Assert.assertThrows;
import static tutorease.address.testutil.TypicalStudents.getTypicalTutorEase;

import org.junit.jupiter.api.Test;

import tutorease.address.commons.exceptions.IllegalValueException;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.lesson.EndDateTime;
import tutorease.address.model.lesson.Fee;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.StartDateTime;
import tutorease.address.model.lesson.StudentId;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.LessonBuilder;

public class JsonAdaptedLessonTest {
    private static final String INVALID_STUDENT = "R@chel";
    private static final String INVALID_FEE = "-1";
    private static final String INVALID_START_DATE_TIME = "00-00-00 25:60";
    private static final String INVALID_END_DATE_TIME = "00-00-00 25:60";
    private final ReadOnlyTutorEase tutorEase = getTypicalTutorEase();

    private final Lesson validLesson = new LessonBuilder().withName(tutorEase.getPersonList().get(0)).build();
    private final String validStudent = validLesson.getStudent().getName().fullName;
    private final String validFee = validLesson.getFeeString();
    private final String validStartDateTime = validLesson.getStartDateTimeString();
    private final String validEndDateTime = validLesson.getEndDateTimeString();

    public JsonAdaptedLessonTest() throws ParseException {
    }

    @Test
    public void toModelType_validLessonsDetails_returnsLesson() throws Exception {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validLesson);
        assertEquals(validLesson, lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_invalidStudent_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(INVALID_STUDENT, validFee,
                validStartDateTime, validEndDateTime);
        String expectedMessage = StudentId.INVALID_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_nullStudent_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(null, validFee, validStartDateTime, validEndDateTime);
        String expectedMessage = String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT,
                Person.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }
    @Test
    public void toModelType_invalidFee_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, INVALID_FEE, validStartDateTime,
                validEndDateTime);
        String expectedMessage = Fee.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }
    @Test
    public void toModelType_nullFee_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, null, validStartDateTime, validEndDateTime);
        String expectedMessage = String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT,
                Fee.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }
    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, validFee, INVALID_START_DATE_TIME,
                validEndDateTime);
        String expectedMessage = StartDateTime.START_DATE_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, validFee, null,
                validEndDateTime);
        String expectedMessage = String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT,
                StartDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, validFee, validStartDateTime,
                INVALID_END_DATE_TIME);
        String expectedMessage = EndDateTime.END_DATE_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, validFee, validStartDateTime, null);
        String expectedMessage = String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT,
                "EndDateTime");
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_startAfterEnd_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, validFee, validEndDateTime, validStartDateTime);
        String expectedMessage = StartDateTime.START_IS_AFTER_END;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }
}

package tutorease.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorease.address.testutil.Assert.assertThrows;
import static tutorease.address.testutil.TypicalStudents.getTypicalTutorEase;

import org.junit.jupiter.api.Test;

import tutorease.address.commons.exceptions.IllegalValueException;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.lesson.EndDateTime;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.LocationIndex;
import tutorease.address.model.lesson.StartDateTime;
import tutorease.address.model.lesson.StudentId;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.LessonBuilder;

public class JsonAdaptedLessonTest {
    private static final String INVALID_STUDENT = "R@chel";
    private static final String INVALID_LOCATION_INDEX = "0";
    private static final String INVALID_START_DATE_TIME = "00-00-00 25:60";
    private static final String INVALID_END_DATE_TIME = "00-00-00 25:60";
    private final ReadOnlyTutorEase tutorEase = getTypicalTutorEase();

    private final Lesson validLesson = new LessonBuilder().withName(tutorEase.getPersonList().get(0)).build();
    private final String validStudent = validLesson.getStudent().getName().fullName;
    private final String validLocationIndex = validLesson.getLocationIndex().toString();
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
    public void toModelType_invalidStudent_throwsIllegalValueException() throws ParseException {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(INVALID_STUDENT, validLocationIndex,
                validStartDateTime, validEndDateTime);
        String expectedMessage = StudentId.INVALID_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_nullStudent_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(null, validLocationIndex,
                validStartDateTime, validEndDateTime);
        String expectedMessage = String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT,
                Person.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_invalidLocationIndex_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, INVALID_LOCATION_INDEX,
                validStartDateTime, validEndDateTime);
        String expectedMessage = LocationIndex.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_nullLocationIndex_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, null,
                validStartDateTime, validEndDateTime);
        String expectedMessage = String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT,
                LocationIndex.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, validLocationIndex,
                INVALID_START_DATE_TIME, validEndDateTime);
        String expectedMessage = StartDateTime.START_DATE_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, validLocationIndex,
                null, validEndDateTime);
        String expectedMessage = String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT,
                StartDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, validLocationIndex,
                validStartDateTime, INVALID_END_DATE_TIME);
        String expectedMessage = EndDateTime.END_DATE_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, validLocationIndex,
                validStartDateTime, null);
        String expectedMessage = String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT,
                "EndDateTime");
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }

    @Test
    public void toModelType_startAfterEnd_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validStudent, validLocationIndex,
                validEndDateTime, validStartDateTime);
        String expectedMessage = StartDateTime.START_IS_AFTER_END;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(tutorEase));
    }
}

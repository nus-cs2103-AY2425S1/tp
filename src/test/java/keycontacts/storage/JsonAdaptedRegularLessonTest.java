package keycontacts.storage;

import static keycontacts.testutil.Assert.assertThrows;
import static keycontacts.testutil.TypicalStudents.ALICE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.model.lesson.Day;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.lesson.Time;

public class JsonAdaptedRegularLessonTest {
    private static final String VALID_DAY = ALICE.getRegularLesson().getLessonDay().toString();
    private static final String VALID_START_TIME = ALICE.getRegularLesson().getStartTime().toString();
    private static final String VALID_END_TIME = ALICE.getRegularLesson().getEndTime().toString();

    private static final String INVALID_DAY = "day";
    private static final String INVALID_START_TIME = "2pm";
    private static final String INVALID_END_TIME = "3pm";

    @Test
    public void toModelType_validRegularLessonDetails_returnsRegularLesson() throws Exception {
        JsonAdaptedRegularLesson jsonAdaptedRegularLesson =
                new JsonAdaptedRegularLesson(VALID_DAY, VALID_START_TIME, VALID_END_TIME);
        RegularLesson regularLesson =
                new RegularLesson(new Day(VALID_DAY), new Time(VALID_START_TIME), new Time(VALID_END_TIME));;
        assertEquals(regularLesson, jsonAdaptedRegularLesson.toModelType());
    }

    @Test
    public void toModelType_invalidDay_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedRegularLesson(
                INVALID_DAY, VALID_START_TIME, VALID_END_TIME).toModelType());
    }

    @Test
    public void toModelType_nullDay_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedRegularLesson(
                null, VALID_START_TIME, VALID_END_TIME).toModelType());
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedRegularLesson(
                VALID_DAY, INVALID_START_TIME, VALID_END_TIME).toModelType());
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedRegularLesson(
                VALID_DAY, null, VALID_END_TIME).toModelType());
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedRegularLesson(
                VALID_DAY, VALID_START_TIME, INVALID_END_TIME).toModelType());
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedRegularLesson(
                VALID_DAY, VALID_START_TIME, null).toModelType());
    }

    @Test
    public void toModelType_startTimeAfterEndTime_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedRegularLesson(
                VALID_DAY, VALID_END_TIME, VALID_START_TIME).toModelType());
    }
}

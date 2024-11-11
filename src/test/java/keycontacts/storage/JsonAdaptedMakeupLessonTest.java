package keycontacts.storage;

import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.Lesson;
import keycontacts.model.lesson.MakeupLesson;
import keycontacts.model.lesson.Time;

public class JsonAdaptedMakeupLessonTest {

    @Test
    public void toModelType_nullLessonDate_throwsIllegalValueException() {
        JsonAdaptedMakeupLesson makeupLesson = new JsonAdaptedMakeupLesson(null, "14:00", "15:00");
        String expectedMessage = String.format(JsonAdaptedMakeupLesson.MISSING_FIELD_MESSAGE_FORMAT,
                Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, makeupLesson::toModelType);
    }

    @Test
    public void toModelType_emptyLessonDate_throwsIllegalValueException() {
        JsonAdaptedMakeupLesson makeupLesson = new JsonAdaptedMakeupLesson("   ", "14:00", "15:00");
        String expectedMessage = String.format(JsonAdaptedMakeupLesson.MISSING_FIELD_MESSAGE_FORMAT,
                Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, makeupLesson::toModelType);
    }

    @Test
    public void toModelType_invalidLessonDate_throwsIllegalValueException() {
        JsonAdaptedMakeupLesson invalidMakeupLesson = new JsonAdaptedMakeupLesson("Test", "15:00", "16:00");
        assertThrows(IllegalValueException.class, Date.MESSAGE_CONSTRAINTS, invalidMakeupLesson::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedMakeupLesson makeupLesson = new JsonAdaptedMakeupLesson("09-10-2025", null, "15:00");
        String expectedMessage = String.format(JsonAdaptedMakeupLesson.MISSING_FIELD_MESSAGE_FORMAT,
                Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, makeupLesson::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JsonAdaptedMakeupLesson makeupLesson = new JsonAdaptedMakeupLesson("09-10-2025", "14:00", null);
        String expectedMessage = String.format(JsonAdaptedMakeupLesson.MISSING_FIELD_MESSAGE_FORMAT,
                Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, makeupLesson::toModelType);
    }

    @Test
    public void toModelType_emptyStartTime_throwsIllegalValueException() {
        JsonAdaptedMakeupLesson makeupLesson = new JsonAdaptedMakeupLesson("09-10-2025", "  ", "15:00");
        assertThrows(IllegalValueException.class, Time.MESSAGE_CONSTRAINTS, makeupLesson::toModelType);
    }

    @Test
    public void toModelType_emptyEndTime_throwsIllegalValueException() {
        JsonAdaptedMakeupLesson makeupLesson = new JsonAdaptedMakeupLesson("09-10-2025", "14:00", " ");
        assertThrows(IllegalValueException.class, Time.MESSAGE_CONSTRAINTS, makeupLesson::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedMakeupLesson makeupLesson = new JsonAdaptedMakeupLesson("09-10-2025", "invalid", "15:00");
        assertThrows(IllegalValueException.class, Time.MESSAGE_CONSTRAINTS, makeupLesson::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        JsonAdaptedMakeupLesson makeupLesson = new JsonAdaptedMakeupLesson("09-10-2025", "14:00", "invalid");
        assertThrows(IllegalValueException.class, Time.MESSAGE_CONSTRAINTS, makeupLesson::toModelType);
    }

    @Test
    public void toModelType_invalidTimePair_throwsIllegalValueException() {
        JsonAdaptedMakeupLesson makeupLesson = new JsonAdaptedMakeupLesson("09-10-2025", "16:00", "15:00");
        assertThrows(IllegalValueException.class, Lesson.MESSAGE_CONSTRAINTS, makeupLesson::toModelType);
    }

    @Test
    public void toModelType_validMakeupLesson_success() throws IllegalValueException {
        JsonAdaptedMakeupLesson validMakeupLesson = new JsonAdaptedMakeupLesson("09-10-2025", "14:00", "15:00");
        assertEquals(new MakeupLesson(new Date("09-10-2025"),
                new Time("14:00"), new Time("15:00")), validMakeupLesson.toModelType());
    }
}

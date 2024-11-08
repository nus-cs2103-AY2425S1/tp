package keycontacts.storage;

import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.model.lesson.MakeupLesson;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.Time;

public class JsonAdaptedMakeupLessonTest {
    @Test
    public void toModelType_invalidMakeupLesson_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedMakeupLesson invalidMakeupLesson = new JsonAdaptedMakeupLesson("Test", "15:00", "16:00");
        assertThrows(IllegalValueException.class, Date.MESSAGE_CONSTRAINTS, invalidMakeupLesson::toModelType);
    }

    @Test
    public void toModelType_validMakeupLesson_success() throws IllegalValueException {
        JsonAdaptedMakeupLesson validMakeupLesson = new JsonAdaptedMakeupLesson("09-10-2025", "14:00", "15:00");
        assertEquals(new MakeupLesson(new Date("09-10-2025"), new Time("14:00"), new Time("15:00")), validMakeupLesson.toModelType());
    }
}

package keycontacts.storage;

import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Date;

public class JsonAdaptedCancelledLessonTest {

    @Test
    public void toModelType_invalidCancelledLesson_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedCancelledLesson invalidCancelledLesson = new JsonAdaptedCancelledLesson("Test");
        assertThrows(IllegalValueException.class, Date.MESSAGE_CONSTRAINTS, invalidCancelledLesson::toModelType);
    }

    @Test
    public void toModelType_validCancelledLesson_success() throws IllegalValueException {
        JsonAdaptedCancelledLesson validCancelledLesson = new JsonAdaptedCancelledLesson("06-07-2002");
        assertEquals(new CancelledLesson(new Date("06-07-2002")), validCancelledLesson.toModelType());
    }
}

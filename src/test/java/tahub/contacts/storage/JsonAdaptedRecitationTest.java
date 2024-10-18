package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.courseclass.recitation.Recitation;

public class JsonAdaptedRecitationTest {

    private final Recitation validRecitation = new Recitation("R01",
            new Course("CS2030S", "Advanced Computer Science"));

    @Test
    public void toModelType_validRecitationDetails_returnsRecitation() throws Exception {
        JsonAdaptedRecitation recitation = new JsonAdaptedRecitation(validRecitation.getRecitationId(),
                validRecitation.getCourse());
        assertEquals(validRecitation, recitation.toModelType());
    }

    @Test
    public void toModelType_invalidRecitationId_throwsIllegalValueException() {
        JsonAdaptedRecitation recitation = new JsonAdaptedRecitation(null, validRecitation.getCourse());
        assertThrows(IllegalValueException.class, recitation::toModelType);
    }

    @Test
    public void toModelType_nullCourse_throwsIllegalValueException() {
        JsonAdaptedRecitation recitation = new JsonAdaptedRecitation(validRecitation.getRecitationId(), null);
        assertThrows(IllegalValueException.class, recitation::toModelType);
    }

    @Test
    public void constructor_validSourceRecitation_convertsSuccessfully() throws IllegalValueException {
        JsonAdaptedRecitation recitation = new JsonAdaptedRecitation(validRecitation);
        assertEquals(validRecitation, recitation.toModelType());
    }
}

package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.tutorial.Tutorial;

public class JsonAdaptedTutorialTest {

    private final Tutorial validTutorial = new Tutorial("T01", new Course("CS1010",
            "Computer Science"));
    @Test
    public void toModelType_validTutorialDetails_returnsTutorial() throws Exception {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(validTutorial.getTutorialId(),
                validTutorial.getCourse());
        assertEquals(validTutorial, tutorial.toModelType());
    }

    @Test
    public void toModelType_invalidTutorialId_throwsIllegalValueException() {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(null, validTutorial.getCourse());
        assertThrows(IllegalValueException.class, tutorial::toModelType);
    }

    @Test
    public void toModelType_nullCourse_throwsIllegalValueException() {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(validTutorial.getTutorialId(), null);
        assertThrows(IllegalValueException.class, tutorial::toModelType);
    }

    @Test
    public void constructor_validSourceTutorial_convertsSuccessfully() throws IllegalValueException {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(validTutorial);
        assertEquals(validTutorial, tutorial.toModelType());
    }
}

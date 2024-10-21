package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import tahub.contacts.commons.exceptions.IllegalValueException;

public class JsonAdaptedTutorialTest {

    private final JsonAdaptedTutorial validAdaptedTutorial = new JsonAdaptedTutorial("T01",
            new JsonAdaptedCourse("CS1010", "Computer Science"));
    @Test
    public void toModelType_validTutorialDetails_returnsTutorial() throws Exception {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(validAdaptedTutorial.toModelType().getTutorialId(),
                validAdaptedTutorial.getAdaptedCourse());
        assertEquals(validAdaptedTutorial.toModelType(), tutorial.toModelType());
    }

    @Test
    public void toModelType_invalidTutorialId_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(null, validAdaptedTutorial.getAdaptedCourse());
        assertThrows(IllegalValueException.class, tutorial::toModelType);
    }

    @Test
    public void toModelType_nullCourse_throwsIllegalValueException() {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(validAdaptedTutorial.getTutorialId(), null);
        assertThrows(IllegalValueException.class, tutorial::toModelType);
    }

    @Test
    public void constructor_validSourceTutorial_convertsSuccessfully() throws IllegalValueException {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(validAdaptedTutorial.getTutorialId(),
                validAdaptedTutorial.getAdaptedCourse());
        assertEquals(validAdaptedTutorial.toModelType(), tutorial.toModelType());
    }
}

package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;

public class JsonAdaptedTaskTest {
    private static final String INVALID_DESCRIPTION = " "; // Description cannot be empty

    private static final String VALID_DESCRIPTION = "Buy medication";
    private static final JsonAdaptedPerson VALID_PATIENT = new JsonAdaptedPerson(BENSON);
    private static final JsonAdaptedPerson INVALID_PATIENT = new JsonAdaptedPerson("R@chel",
            BENSON.getPhone().value,
            BENSON.getEmail().value, BENSON.getAddress().value, BENSON.getEmergencyContact().getName().toString(),
            BENSON.getEmergencyContact().getNumber().toString(),
            BENSON.getTags().stream().map(JsonAdaptedTag::new).toList(),
            BENSON.getPriorityLevel().getValue());

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        Task task = new Task(BENSON, VALID_DESCRIPTION);
        JsonAdaptedTask adaptedTask = new JsonAdaptedTask(task);
        assertEquals(task, adaptedTask.toModelType());
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(null, VALID_PATIENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Description");
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullPatient_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_DESCRIPTION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Patient");
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(INVALID_DESCRIPTION, VALID_PATIENT);
        assertThrows(IllegalValueException.class, task::toModelType);
    }

    @Test
    public void toModelType_invalidPatient_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_DESCRIPTION, INVALID_PATIENT);
        assertThrows(IllegalValueException.class, task::toModelType);
    }
}

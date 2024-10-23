package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;

public class JsonAdaptedAssignmentTest {

    private static final String VALID_TITLE = "Assignment 1";
    private static final String VALID_DUE_DATE = "2024-10-31T23:59"; // ISO LocalDateTime format
    private static final String INVALID_DUE_DATE = "invalid-date";

    @Test
    public void toModelType_validAssignmentDetails_returnsAssignment() throws IllegalValueException {
        HashMap<String, Boolean> validStatuses = new HashMap<>();
        validStatuses.put("1", true);
        validStatuses.put("2", false);

        JsonAdaptedAssignment jsonAssignment = new JsonAdaptedAssignment(VALID_TITLE, VALID_DUE_DATE, validStatuses);
        Assignment assignment = jsonAssignment.toModelType();

        assertEquals(VALID_TITLE, assignment.getTitle());
        assertEquals(LocalDateTime.parse(VALID_DUE_DATE), assignment.getDueDate());
        assertEquals(validStatuses, assignment.getStatuses());
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        HashMap<String, Boolean> validStatuses = new HashMap<>();
        validStatuses.put("1", true);

        JsonAdaptedAssignment jsonAssignment = new JsonAdaptedAssignment(null, VALID_DUE_DATE, validStatuses);
        assertThrows(IllegalValueException.class, jsonAssignment::toModelType);
    }

    @Test
    public void toModelType_nullDueDate_throwsIllegalValueException() {
        HashMap<String, Boolean> validStatuses = new HashMap<>();
        validStatuses.put("1", true);

        JsonAdaptedAssignment jsonAssignment = new JsonAdaptedAssignment(VALID_TITLE, null, validStatuses);
        assertThrows(IllegalValueException.class, jsonAssignment::toModelType);
    }

    @Test
    public void toModelType_invalidDueDate_throwsIllegalValueException() {
        HashMap<String, Boolean> validStatuses = new HashMap<>();
        validStatuses.put("1", true);

        JsonAdaptedAssignment jsonAssignment = new JsonAdaptedAssignment(VALID_TITLE, INVALID_DUE_DATE, validStatuses);
        assertThrows(IllegalValueException.class, jsonAssignment::toModelType);
    }

    @Test
    public void toModelType_emptyStatusMap_returnsAssignmentWithEmptyStatusMap() throws IllegalValueException {
        HashMap<String, Boolean> emptyStatuses = new HashMap<>();

        JsonAdaptedAssignment jsonAssignment = new JsonAdaptedAssignment(VALID_TITLE, VALID_DUE_DATE, emptyStatuses);
        Assignment assignment = jsonAssignment.toModelType();

        assertEquals(VALID_TITLE, assignment.getTitle());
        assertEquals(LocalDateTime.parse(VALID_DUE_DATE), assignment.getDueDate());
        assertEquals(emptyStatuses, assignment.getStatuses());
    }

    @Test
    public void constructorFromAssignment_validAssignment_returnsJsonAdaptedAssignment() {
        HashMap<String, Boolean> statuses = new HashMap<>();
        statuses.put("1", true);
        statuses.put("2", false);

        Assignment assignment = new Assignment(VALID_TITLE, LocalDateTime.parse(VALID_DUE_DATE), statuses);
        JsonAdaptedAssignment jsonAssignment = new JsonAdaptedAssignment(assignment);

        assertEquals(VALID_TITLE, jsonAssignment.getTitle());
        assertEquals(VALID_DUE_DATE, jsonAssignment.getDueDate());
        assertEquals(statuses, jsonAssignment.getIndexToStatusMap());
    }
}

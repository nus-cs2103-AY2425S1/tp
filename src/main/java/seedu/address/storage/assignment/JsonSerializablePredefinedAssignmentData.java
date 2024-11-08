package seedu.address.storage.assignment;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.PredefinedAssignmentsData;

/**
 * Represents a serializable list of predefined assignments for JSON storage.
 */
class JsonSerializablePredefinedAssignmentData {
    private static final String DUPLICATE_PREDEFINED_ASSIGNMENT =
            "Predefined assignments list contains duplicate assignment(s).";
    private static final String MISSING_VALUES = "Assignment is missing assignmentName and/or maxScore.";
    private final List<JsonAdaptedPredefinedAssignment> assignments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePredefinedAssignmentData} with the specified list of adapted assignments.
     *
     * @param assignments List of {@code JsonAdaptedPredefinedAssignment} to initialize with.
     */
    @JsonCreator
    public JsonSerializablePredefinedAssignmentData(
            @JsonProperty("assignments") List<JsonAdaptedPredefinedAssignment> assignments) {
        this.assignments.addAll(assignments);
    }

    /**
     * Converts this predefined assignments into the model's {@code PredefinedAssignmentsData} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PredefinedAssignmentsData toModelType() throws IllegalValueException {
        PredefinedAssignmentsData predefinedAssignmentsData = new PredefinedAssignmentsData();
        for (JsonAdaptedPredefinedAssignment jsonAdaptedPredefinedAssignment : assignments) {
            String assignmentName = jsonAdaptedPredefinedAssignment.getName();
            if (predefinedAssignmentsData.hasAssignment(assignmentName)) {
                throw new IllegalValueException(DUPLICATE_PREDEFINED_ASSIGNMENT);
            }
            if (isFieldNull(jsonAdaptedPredefinedAssignment)) {
                throw new IllegalValueException(MISSING_VALUES);
            }
            predefinedAssignmentsData.addPredefinedAssignment(jsonAdaptedPredefinedAssignment.toModelType());
        }
        return predefinedAssignmentsData;
    }

    private boolean isFieldNull(JsonAdaptedPredefinedAssignment assignment) {
        return assignment.getName() == null || assignment.getMaxScore() == null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JsonSerializablePredefinedAssignmentData other) {
            return other.assignments.equals(assignments);
        }
        return false;
    }
}

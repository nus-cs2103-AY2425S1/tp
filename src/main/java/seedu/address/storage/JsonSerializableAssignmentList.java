package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.AssignmentList;

/**
 * An Immutable AssignmentList that is serializable to JSON format.
 */
public class JsonSerializableAssignmentList {
    private final List<JsonAdaptedAssignment> assignments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAssignmentList} with the given assignments.
     */
    @JsonCreator
    public JsonSerializableAssignmentList(@JsonProperty("assignments") List<JsonAdaptedAssignment> assignments) {
        this.assignments.addAll(assignments);
    }

    /**
     * Converts a given {@code AssignmentList} into this class for Jackson use.
     *
     * @param source the AssignmentList to be converted into a JsonSerializableAssignmentList.
     */
    public JsonSerializableAssignmentList(AssignmentList source) {
        source.getAssignments().forEach(assignment -> assignments.add(new JsonAdaptedAssignment(assignment)));
    }

    /**
     * Converts this Jackson-friendly adapted assignment list object into the model's {@code AssignmentList} object.
     *
     * @return the model's AssignmentList.
     * @throws IllegalValueException if there were any data constraints violated in the adapted assignments.
     */
    public AssignmentList toModelType() throws IllegalValueException {
        AssignmentList assignmentList = new AssignmentList();
        for (JsonAdaptedAssignment jsonAdaptedAssignment : assignments) {
            assignmentList.addAssignment(jsonAdaptedAssignment.toModelType());
        }
        return assignmentList;
    }
}

package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Wraps all data at the assignment data level.
 */
public class PredefinedAssignmentsData implements ReadOnlyPredefinedAssignmentsData {
    private ArrayList<PredefinedAssignment> predefinedAssignmentArrayList = new ArrayList<>();

    public PredefinedAssignmentsData() {
    }

    /**
     * Creates an PredefinedAssignmentsData using the Assignments in the {@code predefinedAssignmentsData}
     */
    public PredefinedAssignmentsData(ReadOnlyPredefinedAssignmentsData predefinedAssignmentsData) {
        this();
        predefinedAssignmentArrayList = predefinedAssignmentsData.getPredefinedList();
    }


    @Override
    public ArrayList<PredefinedAssignment> getPredefinedList() {
        return predefinedAssignmentArrayList;
    }

    /**
     * Creates a predefined assignment using {@code predefinedAssignment}.
     */
    public void addPredefinedAssignment(PredefinedAssignment predefinedAssignment) {
        requireNonNull(predefinedAssignment);
        predefinedAssignmentArrayList.add(predefinedAssignment);
    }

    /**
     * Checks if an assignment is in the database.
     *
     * @param name The name of the assignment to be checked.
     * @return True if present, false otherwise.
     */
    public boolean hasAssignment(String name) {
        for (PredefinedAssignment assignment : predefinedAssignmentArrayList) {
            if (assignment.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public float maxScore(String name) {
        for (PredefinedAssignment assignment : predefinedAssignmentArrayList) {
            if (assignment.name().equalsIgnoreCase(name)) {
                return assignment.maxScore();
            }
        }
        return -1;
    }

    public String getAssignmentName(String name) {
        for (PredefinedAssignment assignment : predefinedAssignmentArrayList) {
            if (assignment.name().equalsIgnoreCase(name)) {
                return assignment.name();
            }
        }
        return null;
    }
}

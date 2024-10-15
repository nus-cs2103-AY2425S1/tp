package seedu.address.model.assignment;

import java.util.ArrayList;

/**
 * Unmodifiable view of predefined assignments.
 */
public interface ReadOnlyPredefinedAssignmentsData {

    /**
     * Returns an unmodifiable view of the assignment list.
     * This list will not contain any assignments.
     */
    ArrayList<PredefinedAssignment> getPredefinedList();
}

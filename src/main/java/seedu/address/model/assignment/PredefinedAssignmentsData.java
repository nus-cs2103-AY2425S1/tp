package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;


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

    /**
     * Returns the max score of an assignment.
     *
     * @param name the name of assignment
     * @return the float (max score)
     */
    public float getMaxScore(String name) {
        for (PredefinedAssignment assignment : predefinedAssignmentArrayList) {
            if (assignment.name().equalsIgnoreCase(name)) {
                return assignment.maxScore();
            }
        }
        return -1;
    }

    /**
     * Retrieves the exact assignment name that matches the given name, ignoring case.
     *
     * @param name the name of the assignment to search for.
     * @return the exact assignment name if found, or {@code null} if no match is found.
     */
    public String getAssignmentName(String name) {
        for (PredefinedAssignment assignment : predefinedAssignmentArrayList) {
            if (assignment.name().equalsIgnoreCase(name)) {
                return assignment.name();
            }
        }
        return null;
    }

    /**
     * Adds a predefined assignment to predefinedAssignmentArrayList.
     * @param p The predefined assignment to add.
     * @return The predefinedAssignmentArrayList.
     */
    public ArrayList<PredefinedAssignment> add(PredefinedAssignment p) {
        predefinedAssignmentArrayList.add(p);
        return predefinedAssignmentArrayList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (predefinedAssignmentArrayList.isEmpty()) {
            return "No assignments specified in database.";
        }
        for (PredefinedAssignment p : predefinedAssignmentArrayList) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PredefinedAssignmentsData other) {
            return predefinedAssignmentArrayList.equals(other.predefinedAssignmentArrayList);
        }
        return false;
    }

}

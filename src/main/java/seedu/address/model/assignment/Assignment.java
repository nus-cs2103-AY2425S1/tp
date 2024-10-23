package seedu.address.model.assignment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Represents an assignment with a title, due dueDate, and completion statuses for students.
 */
public class Assignment {
    private final String title;
    private final LocalDateTime dueDate;
    private final HashMap<String, Boolean> statuses;
    private int numOfCompletedStudents = 0;

    /**
     * Constructs an Assignment with the specified title and due dueDate.
     *
     * @param title The title of the assignment.
     * @param dueDate  The due dueDate of the assignment.
     */
    public Assignment(String title, LocalDateTime dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        this.statuses = new HashMap<>();
    }

    /**
     * Constructs an Assignment with the specified title, due dueDate and statuses.
     *
     * @param title The title of the assignment.
     * @param dueDate  The due dueDate of the assignment.
     * @param statuses The hashmap mapping student indexes to statuses
     */
    public Assignment(String title, LocalDateTime dueDate, HashMap<String, Boolean> statuses) {
        this.title = title;
        this.dueDate = dueDate;
        this.statuses = statuses;
        for (boolean i: statuses.values()) {
            if (i) {
                numOfCompletedStudents++;
            }
        }
    }

    /**
     * Returns the title of the assignment.
     *
     * @return The title of the assignment.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the due dueDate of the assignment.
     *
     * @return The due dueDate of the assignment.
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Retrieves number of completed students.
     */
    public int getNumOfCompletedStudents() {
        return numOfCompletedStudents;
    }

    /**
     * Marks the completion status of a student for this assignment.
     * Updates the number of completed students if the status changes to done.
     *
     * @param index  The index of the student.
     * @param status The completion status of the student (true if completed).
     */
    public void markStatus(String index, boolean status) {
        if (statuses.containsKey(index)) {
            if (status && !statuses.get(index)) {
                numOfCompletedStudents++;
            }
            if (!status && statuses.get(index)) {
                numOfCompletedStudents--;
            }
            statuses.replace(index, status);
        } else {
            statuses.put(index, status);
            if (status) {
                numOfCompletedStudents++;
            }
        }
    }

    /**
     * Retrieves the completion status of a student for this assignment.
     *
     * @param index The index of the student.
     * @return True if the student has completed the assignment, false otherwise.
     */
    public boolean getStatus(String index) {
        if (!statuses.containsKey(index)) {
            return false;
        }
        return statuses.get(index);
    }

    /**
     * Retrieves the hashmap storing all statuses.
     *
     * @return The hashmap of indexes to booleans.
     */
    public HashMap<String, Boolean> getStatuses() {
        return statuses;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Assignment)) {
            return false;
        }
        Assignment otherAssignment = (Assignment) other;
        return otherAssignment.getTitle().equals(getTitle());
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return title + " due on " + formatter.format(dueDate)
                + " (" + numOfCompletedStudents + " students have completed!)";
    }
}

package seedu.address.model.assignment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Represents an assignment with a title, due date, and completion statuses for students.
 */
public class Assignment {
    private final String title;
    private final LocalDateTime date;
    private final HashMap<Integer, Boolean> statuses = new HashMap<>();
    private int numOfCompletedStudents;

    /**
     * Constructs an Assignment with the specified title and due date.
     *
     * @param title The title of the assignment.
     * @param date  The due date of the assignment.
     */
    public Assignment(String title, LocalDateTime date) {
        this.title = title;
        this.date = date;
        this.numOfCompletedStudents = 0;
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
     * Returns the due date of the assignment.
     *
     * @return The due date of the assignment.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Marks the completion status of a student for this assignment.
     * Updates the number of completed students if the status changes to completed.
     *
     * @param index  The index of the student.
     * @param status The completion status of the student (true if completed).
     */
    public void markStatus(int index, boolean status) {
        if (statuses.containsKey(index)) {
            if (status && !statuses.get(index)) {
                numOfCompletedStudents++;
            }
            statuses.replace(index, status);
        } else {
            statuses.put(index, status);
            numOfCompletedStudents++;
        }
    }

    /**
     * Retrieves the completion status of a student for this assignment.
     *
     * @param index The index of the student.
     * @return True if the student has completed the assignment, false otherwise.
     */
    public boolean getStatus(int index) {
        if (!statuses.containsKey(index)) {
            return false;
        }
        return statuses.get(index);
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
        return title + " due on " + formatter.format(date)
                + " (" + numOfCompletedStudents + " students have completed!)";
    }
}

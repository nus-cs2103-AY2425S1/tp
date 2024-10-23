package seedu.address.model.task;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * The Deadline class represents a task with a deadline.
 * It extends the Task class and adds a LocalDate field to store the deadline date.
 */
public class Deadline extends Task {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Date by;


    /**
     * Constructs a Deadline task with the specified description and deadline date.
     *
     * @param description The description of the task.
     * @param by          The deadline date in the format "yyyy-MM-dd".
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = new Date(by);
    }

    /**
     * Constructs a Deadline task with the specified description, deadline date and isDone status.
     *
     * @param description The description of the task.
     * @param by          The deadline date in the format "yyyy-MM-dd".
     * @param isDone      The completion status of the event.
     */
    public Deadline(String description, String by, boolean isDone) {
        super(description);
        this.by = new Date(by);
        this.isDone = isDone;
    }

    public Date getBy() {
        return by;
    }

    /**
     * Returns true if both deadline tasks have the same data fields.
     * This defines a stronger notion of equality between two deadline tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Deadline)) {
            return false;
        }

        Deadline otherDeadline = (Deadline) other;
        return description.equals(otherDeadline.description)
                && isDone == otherDeadline.isDone
                && by.equals(otherDeadline.by);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, isDone, by);
    }

    /**
     * Returns a string representation of the Deadline task, including its deadline date.
     *
     * @return A string representation of the Deadline task.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[D]" + super.toString() + " (by: " + this.by.format(formatter) + ")";
    }
}

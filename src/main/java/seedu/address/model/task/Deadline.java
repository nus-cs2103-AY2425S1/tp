package seedu.address.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The Deadline class represents a task with a deadline.
 * It extends the Task class and adds a LocalDate field to store the deadline date.
 */
public class Deadline extends Task {

    private LocalDate by;

    /**
     * Constructs a Deadline task with the specified description and deadline date.
     *
     * @param description The description of the task.
     * @param by          The deadline date in the format "yyyy-MM-dd".
     */
    public Deadline(String description, String by) {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.by = LocalDate.parse(by, formatter);
    }

    public LocalDate getBy() {
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

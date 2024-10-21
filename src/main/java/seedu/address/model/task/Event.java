package seedu.address.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The Event class represents a task that spans over a period of time.
 * It extends the Task class and adds LocalDate fields to store the start and end dates of the event.
 */
public class Event extends Task {

    private LocalDate from;
    private LocalDate to;

    /**
     * Constructs an Event task with the specified description, start date, and end date.
     *
     * @param description The description of the event.
     * @param from        The start date of the event in the format "yyyy-MM-dd".
     * @param to          The end date of the event in the format "yyyy-MM-dd".
     */
    public Event(String description, String from, String to) {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.from = LocalDate.parse(from, formatter);
        this.to = LocalDate.parse(to, formatter);
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }


    /**
     * Returns true if both event tasks have the same data fields.
     * This defines a stronger notion of equality between two event tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return description.equals(otherEvent.description)
                && isDone == otherEvent.isDone
                && from.equals(otherEvent.from)
                && to.equals(equals(otherEvent.to));
    }

    /**
     * Returns a string representation of the Event task, including its start and end dates.
     *
     * @return A string representation of the Event task.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[E]" + super.toString() + " (from: " + this.from.format(formatter)
                + " to: " + this.to.format(formatter) + ")";
    }
}

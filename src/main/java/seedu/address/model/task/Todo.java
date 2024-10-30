package seedu.address.model.task;

import java.util.Objects;

/**
 * The Todo class represents a simple task with no specific date attached.
 * It extends the Task class and does not add any additional fields.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    public Todo(Description description) {
        super(description);
    }

    /**
     * Constructs a Todo task with the specified description and isDone status.
     *
     * @param description The description of the todo task.
     * @param isDone      The completion status of the event.
     */
    public Todo(String description, boolean isDone) {
        super(description);
        this.isDone = isDone;
    }

    /**
     * Constructs a Todo task with the specified Description type description and isDone status.
     *
     * @param description The description of the todo task.
     * @param isDone      The completion status of the event.
     */
    public Todo(Description description, boolean isDone) {
        super(description);
        this.isDone = isDone;
    }

    /**
     * Returns true if both Todo tasks have the same data fields.
     * This defines a stronger notion of equality between two Todo tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Todo)) {
            return false;
        }

        Todo otherTodo = (Todo) other;
        return description.equals(otherTodo.description)
                && isDone == otherTodo.isDone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, isDone);
    }

    /**
     * Returns a string representation of the Todo task.
     *
     * @return A string representation of the Todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

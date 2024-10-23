package seedu.address.model.task;

import java.util.Objects;

/**
 * The Task class represents a general task with a description and a completion status.
 * It serves as the base class for more specific types of tasks such as Todo, Deadline, and Event.
 */
public class Task {
    protected Description description;
    protected boolean isDone;

    /**
     * Constructs a Task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = new Description(description);
        this.isDone = false;
    }

    /**
     * Constructs a Task with the specified Description type description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(Description description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     * "X" if the task is done, " " if the task is not done.
     *
     * @return The status icon of the task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the description of the task.
     *
     * @return The description of the task as a String.
     */
    public String getDescription() {
        return this.description.toString();
    }

    /**
     * Returns the isDone status the task.
     *
     * @return The completion status of the task as a Boolean.
     */
    public Boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsUndone() {
        isDone = false;
    }

    /**
     * Returns the presence of the given keyword if it is
     * within a partial/full word in the description, case-insensitive.
     * "true" if the keyword is present, "false" if the keyword is not.
     *
     * @param keyword The supplied keyword string.
     * @return The boolean indicating presence of the keyword
     */
    public boolean hasKeywordInPartialDescription(String keyword) {
        String lowerCaseKeyword = keyword.toLowerCase();
        String[] descriptionArray = description.toString().split(" ");
        for (String word : descriptionArray) {
            String lowerCaseWord = word.toLowerCase();
            if (lowerCaseWord.contains(lowerCaseKeyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if both tasks have the same description.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getDescription().equals(getDescription());
    }

    /**
     * Returns true if both tasks have the same data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return description.equals(otherTask.description)
                && isDone == otherTask.isDone;
    }

    /**
     * Returns a string representation of the Task.
     *
     * @return A string representation of the Task, including its status and description.
     */
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, isDone);
    }
}



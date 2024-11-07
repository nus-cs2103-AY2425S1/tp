package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.SimpleBooleanProperty;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    private final Person patient;

    private final String description;

    private final SimpleBooleanProperty isComplete;

    /**
     * Every field must be present and not null.
     */
    public Task(Person patient, String description) {
        requireAllNonNull(patient, description);
        this.patient = patient;
        this.description = description;
        this.isComplete = new SimpleBooleanProperty(false);
    }

    /**
     * Every field must be present and not null.
     */
    public Task(Person patient, String description, boolean isComplete) {
        requireAllNonNull(patient, description, isComplete);
        this.patient = patient;
        this.description = description;
        this.isComplete = new SimpleBooleanProperty(isComplete);
    }

    public Person getPatient() {
        return patient;
    }

    public String getDescription() {
        return description;
    }

    public boolean getStatus() {
        return this.isComplete.getValue();
    }

    public SimpleBooleanProperty isCompleteProperty() {
        return isComplete;
    }

    public String getStatusString() {
        return this.getStatus() ? "Complete" : "Incomplete";
    }

    public void markTaskComplete() {
        this.isComplete.set(true);
    }

    public void markTaskIncomplete() {
        this.isComplete.set(false);
    }

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
        return patient.equals(otherTask.patient)
                && description.equals(otherTask.description);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(patient, description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("patient", patient)
                .add("description", description)
                .add("status", getStatusString())
                .toString();
    }

}

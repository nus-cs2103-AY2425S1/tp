package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_DESCRIPTION = "Buy medication";
    public static final Name DEFAULT_PATIENT_NAME = new Name("John Doe");

    private String description;
    private Person patient;
    private boolean isComplete;

    /**
     * Initializes the TaskBuilder with the default task details.
     */
    public TaskBuilder() {
        this.description = DEFAULT_DESCRIPTION;
        this.patient = new PersonBuilder().withName(DEFAULT_PATIENT_NAME.fullName).build();
        // Tasks are incomplete by default
        this.isComplete = false;
    }

    /**
     * Sets the {@code description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code patient} of the {@code Task} that we are building.
     */
    public TaskBuilder withPatient(Person patient) {
        this.patient = patient;
        return this;
    }

    /**
     * Marks the task as complete.
     * @return this builder instance to allow for chaining of methods
     */
    public TaskBuilder markTaskComplete() {
        this.isComplete = true;
        return this;
    }

    /**
     * Builds and returns the {@code Task} with the specified attributes.
     */
    public Task build() {
        return new Task(patient, description, isComplete);
    }
}

package seedu.address.testutil;

import seedu.address.logic.commands.UpdateTaskCommand.UpdateTaskDescriptor;
import seedu.address.model.person.task.Task;
import seedu.address.model.person.task.TaskDeadline;
import seedu.address.model.person.task.TaskDescription;

/**
 * A utility class to help build {@code UpdateTaskDescriptor} objects for testing.
 */
public class UpdateTaskDescriptorBuilder {

    private UpdateTaskDescriptor descriptor;

    /**
     * Constructs a {@code UpdateTaskDescriptorBuilder} with default values.
     */
    public UpdateTaskDescriptorBuilder() {
        descriptor = new UpdateTaskDescriptor();
    }

    /**
     * Constructs a {@code UpdateTaskDescriptorBuilder} with the values of the given {@code UpdateTaskDescriptor}.
     *
     * @param descriptor The existing {@code UpdateTaskDescriptor} to copy values from.
     */
    public UpdateTaskDescriptorBuilder(UpdateTaskDescriptor descriptor) {
        this.descriptor = new UpdateTaskDescriptor(descriptor);
    }

    /**
     * Constructs a {@code UpdateTaskDescriptorBuilder} with specified task description and deadline.
     *
     * @param task The task to set.
     */
    public UpdateTaskDescriptorBuilder(Task task) {
        descriptor = new UpdateTaskDescriptor();
        descriptor.setTaskDescription(task.getTaskDescription());
        descriptor.setTaskDeadline(task.getTaskDeadline());
    }

    /**
     * Constructs a {@code UpdateTaskDescriptorBuilder} with specified task description and deadline.
     *
     * @param taskDescription The task description to set.
     * @param taskDeadline The task deadline to set.
     */
    public UpdateTaskDescriptorBuilder(TaskDescription taskDescription, TaskDeadline taskDeadline) {
        descriptor = new UpdateTaskDescriptor();
        descriptor.setTaskDescription(taskDescription);
        descriptor.setTaskDeadline(taskDeadline);
    }

    /**
     * Sets the {@code TaskDescription} of the {@code UpdateTaskDescriptor} that we are building.
     *
     * @param taskDescription The task description to set.
     * @return This builder instance for chaining.
     */
    public UpdateTaskDescriptorBuilder withTaskDescription(String taskDescription) {
        descriptor.setTaskDescription(new TaskDescription(taskDescription));
        return this;
    }

    /**
     * Sets the {@code TaskDeadline} of the {@code UpdateTaskDescriptor} that we are building.
     *
     * @param taskDeadline The task deadline to set.
     * @return This builder instance for chaining.
     */
    public UpdateTaskDescriptorBuilder withTaskDeadline(String taskDeadline) {
        descriptor.setTaskDeadline(new TaskDeadline(taskDeadline));
        return this;
    }

    /**
     * Builds and returns the constructed {@code UpdateTaskDescriptor}.
     *
     * @return The constructed {@code UpdateTaskDescriptor} with the specified values.
     */
    public UpdateTaskDescriptor build() {
        return descriptor;
    }
}

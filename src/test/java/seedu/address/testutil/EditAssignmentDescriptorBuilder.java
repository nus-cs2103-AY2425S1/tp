package seedu.address.testutil;

import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;


/**
 * A utility class to help with building EditAssignmentDescriptor objects.
 */
public class EditAssignmentDescriptorBuilder {
    private EditAssignmentCommand.EditAssignmentDescriptor descriptor;

    public EditAssignmentDescriptorBuilder() {
        descriptor = new EditAssignmentCommand.EditAssignmentDescriptor();
    }

    public EditAssignmentDescriptorBuilder(EditAssignmentCommand.EditAssignmentDescriptor descriptor) {
        this.descriptor = new EditAssignmentCommand.EditAssignmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAssignmentDescriptor} with fields containing {@code assignment}'s details
     */
    public EditAssignmentDescriptorBuilder(Assignment assignment) {
        descriptor = new EditAssignmentCommand.EditAssignmentDescriptor();
        descriptor.setName(assignment.getAssignmentName());
        descriptor.setMaxScore(assignment.getMaxScore());
    }

    /**
     * Sets the {@code AssignmentName} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withAssignmentName(AssignmentName name) {
        descriptor.setName(name);
        return this;
    }

    /**
     * Sets the {@code maxScore} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withMaxScore(int maxScore) {
        descriptor.setMaxScore(maxScore);
        return this;
    }

    public EditAssignmentCommand.EditAssignmentDescriptor build() {
        return descriptor;
    }
}

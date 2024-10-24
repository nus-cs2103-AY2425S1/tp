package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;

/**
 * A utility class to help with building EditStudentDescriptor objects.
 */
public class EditStudentDescriptorBuilder {

    private EditCommand.EditStudentDescriptor descriptor;

    public EditStudentDescriptorBuilder() {
        descriptor = new EditStudentDescriptor();
    }

    public EditStudentDescriptorBuilder(EditCommand.EditStudentDescriptor descriptor) {
        this.descriptor = new EditCommand.EditStudentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditStudentDescriptor} with fields containing {@code student}'s details.
     */
    public EditStudentDescriptorBuilder(Student student) {
        descriptor = new EditCommand.EditStudentDescriptor();
        descriptor.setName(student.getName());
        descriptor.setStudentId(student.getStudentId());
        descriptor.setTutorialId(student.getTutorialId());
    }

    /**
     * Sets the {@code Name} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and sets it to the {@code EditStudentDescriptor}
     * Sets the {@code StudentId} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withStudentId(String studentId) {
        descriptor.setStudentId(new StudentId(studentId));
        return this;
    }

    /**
     * Sets the {@code TutorialId} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withTutorialId(String tutorialId) {
        descriptor.setTutorialId(TutorialId.of(tutorialId));
        return this;
    }


    public EditCommand.EditStudentDescriptor build() {
        return descriptor;
    }
}

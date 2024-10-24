package seedu.address.testutil;

import seedu.address.logic.commands.EditStudentCommand.EditStudentDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.TutorialGroup;


/**
 * A utility class to help with building EditStudentDescriptor objects.
 */
public class EditStudentDescriptorBuilder {

    private final EditStudentDescriptor descriptor;

    public EditStudentDescriptorBuilder() {
        descriptor = new EditStudentDescriptor();
    }

    public EditStudentDescriptorBuilder(EditStudentDescriptor descriptor) {
        this.descriptor = new EditStudentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditStudentDescriptor} with fields containing {@code student}'s details
     */
    public EditStudentDescriptorBuilder(Student student) {
        descriptor = new EditStudentDescriptor();
        descriptor.setName(student.getName());
        descriptor.setPhone(student.getPhone());
        descriptor.setTutorialGroup(student.getTutorialGroup());
        descriptor.setStudentNumber(student.getStudentNumber());
    }

    /**
     * Sets the {@code Name} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code TutorialGroup();} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withTutorialGroup(String tutorialGroup) {
        descriptor.setTutorialGroup(new TutorialGroup(tutorialGroup));
        return this;
    }

    /**
     * Sets the {@code StudentNumber} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withStudentNumber(String studentNumber) {
        descriptor.setStudentNumber(new StudentNumber(studentNumber));
        return this;
    }

    public EditStudentDescriptor build() {
        return descriptor;
    }
}


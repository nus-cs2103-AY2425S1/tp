package keycontacts.testutil;

import keycontacts.logic.commands.FindCommand.FindStudentDescriptor;
import keycontacts.model.student.Student;

/**
 * A utility class to help with building FindStudentDescriptor objects.
 */
public class FindStudentDescriptorBuilder {
    private FindStudentDescriptor descriptor;

    public FindStudentDescriptorBuilder() {
        descriptor = new FindStudentDescriptor();
    }

    public FindStudentDescriptorBuilder(FindStudentDescriptor descriptor) {
        this.descriptor = new FindStudentDescriptor(descriptor);
    }

    /**
     * Returns a {@code FindStudentDescriptor} with fields containing
     * {@code student}'s details
     */
    public FindStudentDescriptorBuilder(Student student) {
        descriptor = new FindStudentDescriptor();
        descriptor.setName(student.getName().fullName);
        descriptor.setPhone(student.getPhone().value);
        descriptor.setAddress(student.getAddress().value);
        descriptor.setGradeLevel(student.getGradeLevel().value);
    }

    /**
     * Sets the {@code Name} of the {@code FindStudentDescriptor} that we are
     * building.
     */
    public FindStudentDescriptorBuilder withName(String name) {
        descriptor.setName(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code FindStudentDescriptor} that we are
     * building.
     */
    public FindStudentDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(phone);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code FindStudentDescriptor} that we are
     * building.
     */
    public FindStudentDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(address);
        return this;
    }

    /**
     * Sets the {@code GradeLevel} of the {@code FindStudentDescriptor} that we are
     * building.
     */
    public FindStudentDescriptorBuilder withGradeLevel(String gradeLevel) {
        descriptor.setGradeLevel(gradeLevel);
        return this;
    }

    public FindStudentDescriptor build() {
        return descriptor;
    }
}

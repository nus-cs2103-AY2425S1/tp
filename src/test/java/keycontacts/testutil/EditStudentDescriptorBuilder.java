package keycontacts.testutil;

import keycontacts.logic.commands.EditCommand.EditStudentDescriptor;
import keycontacts.model.student.Address;
import keycontacts.model.student.GradeLevel;
import keycontacts.model.student.Group;
import keycontacts.model.student.Name;
import keycontacts.model.student.Phone;
import keycontacts.model.student.Student;

/**
 * A utility class to help with building EditStudentDescriptor objects.
 */
public class EditStudentDescriptorBuilder {

    private EditStudentDescriptor descriptor;

    public EditStudentDescriptorBuilder() {
        descriptor = new EditStudentDescriptor();
    }

    public EditStudentDescriptorBuilder(EditStudentDescriptor descriptor) {
        this.descriptor = new EditStudentDescriptor(descriptor);
    }

    /**
     * Returns a {@code EditStudentDescriptor} with fields containing {@code student}'s details
     */
    public EditStudentDescriptorBuilder(Student student) {
        descriptor = new EditStudentDescriptor();
        descriptor.setName(student.getName());
        descriptor.setPhone(student.getPhone());
        descriptor.setAddress(student.getAddress());
        descriptor.setGradeLevel(student.getGradeLevel());
        descriptor.setGroup(student.getGroup());
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
     * Sets the {@code Address} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code GradeLevel} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withGradeLevel(String gradeLevel) {
        descriptor.setGradeLevel(new GradeLevel(gradeLevel));
        return this;
    }

    /**
     * Sets the {@code Group} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withGroup(String group) {
        descriptor.setGroup(new Group(group));
        return this;
    }

    public EditStudentDescriptor build() {
        return descriptor;
    }
}

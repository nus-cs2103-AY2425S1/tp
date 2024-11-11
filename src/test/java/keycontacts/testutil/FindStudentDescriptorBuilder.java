package keycontacts.testutil;

import keycontacts.logic.commands.FindCommand.FindStudentDescriptor;

/**
 * A utility class to help with building FindStudentDescriptor objects.
 */
public class FindStudentDescriptorBuilder {
    private FindStudentDescriptor descriptor;

    public FindStudentDescriptorBuilder() {
        descriptor = new FindStudentDescriptor();
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

    /**
     * Sets the {@code Group} of the {@code FindStudentDescriptor} that we are
     * building.
     */
    public FindStudentDescriptorBuilder withGroup(String group) {
        descriptor.setGroup(group);
        return this;
    }


    public FindStudentDescriptor build() {
        return descriptor;
    }
}

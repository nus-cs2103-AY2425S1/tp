package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.model.Student.Address;
import seedu.address.model.Student.Email;
import seedu.address.model.Student.Name;
import seedu.address.model.Student.OwedAmount;
import seedu.address.model.Student.Paid;
import seedu.address.model.Student.Student;
import seedu.address.model.Student.Phone;
import seedu.address.model.Student.Rate;
import seedu.address.model.Student.Schedule;
import seedu.address.model.Student.Subject;

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
     * Returns an {@code EditStudentDescriptor} with fields containing {@code student}'s details
     */
    public EditStudentDescriptorBuilder(Student student) {
        descriptor = new EditStudentDescriptor();
        descriptor.setName(student.getName());
        descriptor.setPhone(student.getPhone());
        descriptor.setEmail(student.getEmail());
        descriptor.setAddress(student.getAddress());
        descriptor.setSchedule(student.getSchedule());
        descriptor.setSubject(student.getSubject());
        descriptor.setRate(student.getRate());
        descriptor.setPaid(student.getPaid());
        descriptor.setOwedAmount(student.getOwedAmount());
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
     * Sets the {@code Email} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
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
     * Sets the {@code Address} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withSchedule(String schedule) {
        descriptor.setSchedule(new Schedule(schedule));
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withSubject(String subject) {
        descriptor.setSubject(new Subject(subject));
        return this;
    }

    /**
     * Sets the {@code Rate} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withRate(String rate) {
        descriptor.setRate(new Rate(rate));
        return this;
    }
    /**
     * Sets the {@code Paid} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withPaid(String paid) {
        descriptor.setPaid(new Paid(paid));
        return this;
    }

    /**
     * Sets the {@code OwedAmount} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withOwedAmount(String owedAmount) {
        descriptor.setOwedAmount(new OwedAmount(owedAmount));
        return this;
    }

    public EditStudentDescriptor build() {
        return descriptor;
    }
}

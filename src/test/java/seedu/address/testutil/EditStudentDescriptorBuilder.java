package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.PaidAmount;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Rate;
import seedu.address.model.student.Schedule;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;

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
        descriptor.setPaidAmount(student.getPaidAmount());
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
     * Sets the {@code PaidAmount} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withPaidAmount(String paidAmount) {
        descriptor.setPaidAmount(new PaidAmount(paidAmount));
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

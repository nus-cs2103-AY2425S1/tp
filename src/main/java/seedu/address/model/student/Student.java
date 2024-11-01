package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Schedule schedule;
    private final Subject subject;
    private final Rate rate;
    private final PaidAmount paidAmount;
    private final OwedAmount owedAmount;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Address address, Schedule schedule,
            Subject subject, Rate rate, PaidAmount paidAmount, OwedAmount owedAmount) {
        requireAllNonNull(name, phone, email, address, schedule, subject, rate, paidAmount, owedAmount);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.schedule = schedule;
        this.subject = subject;
        this.rate = rate;
        this.paidAmount = paidAmount;
        this.owedAmount = owedAmount;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Subject getSubject() {
        return subject;
    }

    public Rate getRate() {
        return rate;
    }
    public PaidAmount getPaidAmount() {
        return paidAmount;
    }

    public OwedAmount getOwedAmount() {
        return owedAmount;
    }

    public double getPaidAmountValue() {
        return paidAmount.getValue();
    }

    public double getOwedAmountValue() {
        return owedAmount.getValue();
    }

    /**
     * Returns true if both students have the same name.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName())
                && otherStudent.getPhone().equals(getPhone());
    }

    /**
     * Determines whether there is a scheduling conflict between this student and another student.
     * @param otherStudent The student to compare against for scheduling conflicts.
     * @return true if there is a clash in schedules with the specified other student.
     */
    public boolean isClash(Student otherStudent) {
        if (otherStudent == this) {
            return false;
        }

        return otherStudent != null && getSchedule().isClash(otherStudent.getSchedule());
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return name.equals(otherStudent.name)
                && phone.equals(otherStudent.phone)
                && email.equals(otherStudent.email)
                && address.equals(otherStudent.address)
                && schedule.equals(otherStudent.schedule)
                && subject.equals(otherStudent.subject)
                && rate.equals(otherStudent.rate)
                && paidAmount.equals(otherStudent.paidAmount)
                && owedAmount.equals(otherStudent.owedAmount);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, schedule, subject, rate, paidAmount, owedAmount);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("schedule", schedule)
                .add("subject", subject)
                .add("rate", rate)
                .add("paidAmount", paidAmount)
                .add("owedAmount", owedAmount)
                .toString();
    }

}

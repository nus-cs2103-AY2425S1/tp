package seedu.address.testutil;

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
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SCHEDULE = "Sunday-1800-1900";
    public static final String DEFAULT_SUBJECT = "Mathematics";
    public static final String DEFAULT_FEE = "300";
    public static final String DEFAULT_PAID = "600";
    public static final String DEFAULT_OWED_AMOUNT = "300";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Schedule schedule;
    private Subject subject;
    private Rate rate;
    private PaidAmount paidAmount;
    private OwedAmount owedAmount;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        schedule = new Schedule(DEFAULT_SCHEDULE);
        subject = new Subject(DEFAULT_SUBJECT);
        rate = new Rate(DEFAULT_FEE);
        paidAmount = new PaidAmount(DEFAULT_PAID);
        owedAmount = new OwedAmount(DEFAULT_OWED_AMOUNT);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        address = studentToCopy.getAddress();
        schedule = studentToCopy.getSchedule();
        subject = studentToCopy.getSubject();
        rate = studentToCopy.getRate();
        paidAmount = studentToCopy.getPaidAmount();
        owedAmount = studentToCopy.getOwedAmount();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }


    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public StudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Schedule} of the {@code Student} that we are building.
     */
    public StudentBuilder withSchedule(String schedule) {
        this.schedule = new Schedule(schedule);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Student} that we are building.
     */
    public StudentBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Sets the {@code Rate} of the {@code Student} that we are building.
     */
    public StudentBuilder withRate(String rate) {
        this.rate = new Rate(rate);
        return this;
    }

    /**
     * Sets the {@code PaidAmount} of the {@code Student} that we are building.
     */
    public StudentBuilder withPaidAmount(String paidAmount) {
        this.paidAmount = new PaidAmount(paidAmount);
        return this;
    }

    /**
     * Sets the {@code OwedAmount} of the {@code Student} that we are building.
     */
    public StudentBuilder withOwedAmount(String owedAmount) {
        this.owedAmount = new OwedAmount(owedAmount);
        return this;
    }

    public Student build() {
        return new Student(name, phone, email, address, schedule, subject, rate, paidAmount, owedAmount);
    }

}

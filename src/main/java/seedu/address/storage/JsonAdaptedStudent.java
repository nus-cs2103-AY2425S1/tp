package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String schedule;
    private final String subject;
    private final String rate;
    private final String paidAmount;
    private final String owedAmount;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
                    @JsonProperty("schedule") String schedule, @JsonProperty("subject") String subject,
                            @JsonProperty("rate") String rate, @JsonProperty("paidAmount") String paidAmount,
                                    @JsonProperty("owedAmount") String owedAmount) {
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

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        schedule = source.getSchedule().value;
        subject = source.getSubject().toString();
        rate = source.getRate().toString();
        paidAmount = source.getPaidAmount().toString();
        owedAmount = source.getOwedAmount().toString();
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (schedule == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Schedule.class.getSimpleName()));
        }
        if (!Schedule.isValidSchedule(schedule)) {
            throw new IllegalValueException(Schedule.MESSAGE_CONSTRAINTS);
        }
        final Schedule modelSchedule = new Schedule(schedule);

        if (subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Subject.class.getSimpleName()));
        }
        if (!Subject.isValidSubject(subject)) {
            throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
        }
        final Subject modelSubject = new Subject(subject);

        if (rate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rate.class.getSimpleName()));
        }
        if (!Rate.isValidRate(rate)) {
            throw new IllegalValueException(Rate.MESSAGE_CONSTRAINTS);
        }
        final Rate modelRate = new Rate(rate);
        if (paidAmount == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, PaidAmount.class.getSimpleName()));
        }
        if (!PaidAmount.isValidPaidAmount(paidAmount)) {
            throw new IllegalValueException(PaidAmount.MESSAGE_CONSTRAINTS);
        }
        final PaidAmount modelPaidAmount = new PaidAmount(paidAmount);

        if (owedAmount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OwedAmount.class.getSimpleName()));
        }
        if (!OwedAmount.isValidOwedAmount(owedAmount)) {
            throw new IllegalValueException(OwedAmount.MESSAGE_CONSTRAINTS);
        }
        final OwedAmount modelOwedAmount = new OwedAmount(owedAmount);

        return new Student(modelName, modelPhone, modelEmail, modelAddress, modelSchedule,
                modelSubject, modelRate, modelPaidAmount, modelOwedAmount);
    }

}

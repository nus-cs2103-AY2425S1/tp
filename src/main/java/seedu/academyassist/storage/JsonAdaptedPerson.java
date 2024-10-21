package seedu.academyassist.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.academyassist.commons.exceptions.IllegalValueException;
import seedu.academyassist.model.person.Address;
import seedu.academyassist.model.person.Email;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Name;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.Phone;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.YearGroup;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String ic;
    private final String yearGroup;
    private final String studentId;
    private final List<JsonAdaptedSubject> subjects = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("ic") String ic, @JsonProperty("year group") String yearGroup,
            @JsonProperty("student id") String studentId,
            @JsonProperty("subject") List<JsonAdaptedSubject> subjects) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.ic = ic;
        this.yearGroup = yearGroup;
        this.studentId = studentId;
        if (subjects != null) {
            this.subjects.addAll(subjects);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        ic = source.getIc().value;
        yearGroup = source.getYearGroup().value;
        studentId = source.getStudentId().value;
        subjects.addAll(source.getSubjects().stream()
                .map(JsonAdaptedSubject::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
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

        if (ic == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Ic.class.getSimpleName()));
        }
        if (!Ic.isValidIc(ic)) {
            throw new IllegalValueException(Ic.MESSAGE_CONSTRAINTS);
        }
        final Ic modelIc = new Ic(ic);
        if (yearGroup == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    YearGroup.class.getSimpleName()));
        }
        if (!YearGroup.isValidYearGroup(yearGroup)) {
            throw new IllegalValueException(YearGroup.MESSAGE_CONSTRAINTS);
        }
        final YearGroup modelYearGroup = new YearGroup(yearGroup);

        if (studentId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentId.class.getSimpleName()));
        }
        if (!StudentId.isValidStudentId(studentId)) {
            throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
        }
        final StudentId modelStudentId = new StudentId(studentId);

        if (subjects == null || subjects.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName()));
        }
        final List<Subject> personSubjects = new ArrayList<>();
        for (JsonAdaptedSubject subject : subjects) {
            if (!Subject.isValidSubject(String.valueOf(subject))) {
                throw new IllegalValueException(Subject.MESSAGE_CONSTRAINTS);
            }
            personSubjects.add(subject.toModelType());
        }

        final Set<Subject> modelSubjects = new HashSet<>();
        for (JsonAdaptedSubject subject : subjects) {
            modelSubjects.add(subject.toModelType());
        }

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelIc, modelYearGroup, modelStudentId,
                modelSubjects);
    }

}

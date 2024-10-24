package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.exam.Exam;
import seedu.address.model.person.AbsentDate;
import seedu.address.model.person.AbsentReason;
import seedu.address.model.person.Address;
import seedu.address.model.person.EcName;
import seedu.address.model.person.EcNumber;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RegisterNumber;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StudentClass;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String registerNumber;
    private final String sex;
    private final String studentClass;
    private final String ecName;
    private final String ecNumber;
    private final Map<String, String> attendances;

    private final List<JsonAdaptedExam> exams = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("register number") String registerNumber, @JsonProperty("sex") String sex,
                             @JsonProperty("class") String studentClass,
                             @JsonProperty("emergency contact name") String ecName,
                             @JsonProperty("emergency contact number") String ecNumber,
                             @JsonProperty("exams") List<JsonAdaptedExam> exams,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("attendances") Map<String, String> attendances) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.registerNumber = registerNumber;
        this.sex = sex;
        this.studentClass = studentClass;
        this.ecName = ecName;
        this.ecNumber = ecNumber;
        if (exams != null) {
            this.exams.addAll(exams);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (attendances != null) {
            this.attendances = new HashMap<>(attendances);
        } else {
            this.attendances = new HashMap<>();
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
        registerNumber = source.getRegisterNumber().value;
        sex = source.getSex().value;
        studentClass = source.getStudentClass().value;
        ecName = source.getEcName().value;
        ecNumber = source.getEcNumber().value;
        exams.addAll(source.getExams().stream()
                .map(JsonAdaptedExam::new)
                .collect(Collectors.toList()));
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        attendances = new HashMap<>();
        source.getAttendances().forEach((date, reason) -> {
            attendances.put(date.toString(), reason.toString());
        });
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Exam> personExams = new ArrayList<>();
        for (JsonAdaptedExam exam : exams) {
            personExams.add(exam.toModelType());
        }

        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

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

        if (registerNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, RegisterNumber.class
                    .getSimpleName()));
        }
        if (!RegisterNumber.isValidRegisterNumber(registerNumber)) {
            throw new IllegalValueException(RegisterNumber.MESSAGE_CONSTRAINTS);
        }
        final RegisterNumber modelRegisterNumber = new RegisterNumber(registerNumber);

        if (sex == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sex.class.getSimpleName()));
        }
        if (!Sex.isValidSex(sex)) {
            throw new IllegalValueException(Sex.MESSAGE_CONSTRAINTS);
        }
        final Sex modelSex = new Sex(sex);

        if (studentClass == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentClass.class.getSimpleName()));
        }
        if (!StudentClass.isValidStudentClass(studentClass)) {
            throw new IllegalValueException(StudentClass.MESSAGE_CONSTRAINTS);
        }
        final StudentClass modelStudentClass = new StudentClass(studentClass);

        if (ecName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EcName.class.getSimpleName()));
        }
        if (!EcName.isValidEcName(ecName)) {
            throw new IllegalValueException(EcName.MESSAGE_CONSTRAINTS);
        }
        final EcName modelEcName = new EcName(ecName);

        if (ecNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EcNumber.class.getSimpleName()));
        }
        if (!EcNumber.isValidEcNumber(ecNumber)) {
            throw new IllegalValueException(EcNumber.MESSAGE_CONSTRAINTS);
        }
        final EcNumber modelEcNumber = new EcNumber(ecNumber);

        final Set<Exam> modelExams = new HashSet<>(personExams);
        final Set<Tag> modelTags = new HashSet<>(personTags);

        final HashMap<AbsentDate, AbsentReason> modelAttendances = new HashMap<>();
        if (attendances != null) {
            for (Map.Entry<String, String> entry : attendances.entrySet()) {
                String dateStr = entry.getKey();
                String reasonStr = entry.getValue();

                if (!AbsentDate.isValidAbsentDate(dateStr)) {
                    System.out.println("Invalid date detected: " + dateStr);
                    throw new IllegalValueException(AbsentDate.MESSAGE_CONSTRAINTS);
                }

                if (!AbsentReason.isValidAbsentReason(reasonStr)) {
                    throw new IllegalValueException(AbsentReason.MESSAGE_CONSTRAINTS);
                }

                modelAttendances.put(new AbsentDate(dateStr), new AbsentReason(reasonStr));
            }
        }

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelRegisterNumber, modelSex,
                modelStudentClass, modelEcName, modelEcNumber, modelExams, modelTags, modelAttendances);
    }
}

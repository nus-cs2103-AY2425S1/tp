package seedu.address.storage;

import static seedu.address.model.person.Student.STUDENT_TYPE;
import static seedu.address.model.person.Teacher.TEACHER_TYPE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DaysAttended;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Teacher;
import seedu.address.model.person.exceptions.InvalidPersonTypeException;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String type;
    private final String name;
    private final String gender;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedSubject> subjects = new ArrayList<>();
    private final List<String> classes = new ArrayList<>();
    private final Integer daysAttended;
    private final String nextOfKin;
    private final String emergencyContact;


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("type") String type, @JsonProperty("name") String name,
                             @JsonProperty("gender") String gender,
                             @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                             @JsonProperty("address") String address, @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("subjects") List<JsonAdaptedSubject> subjects,
                             @JsonProperty("classes") List<String> classes,
                             @JsonProperty("daysAttended") Integer daysAttended,
                             @JsonProperty("nextOfKin") String nextOfKin,
                             @JsonProperty("emergencyContact") String emergencyContact) {
        this.type = type;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (subjects != null) {
            this.subjects.addAll(subjects);
        }
        if (classes != null) {
            this.classes.addAll(classes);
        }
        this.daysAttended = daysAttended;
        this.nextOfKin = nextOfKin;
        this.emergencyContact = emergencyContact;
    }

    /**
     * Converts a given {@code Teacher} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Teacher source) {
        type = TEACHER_TYPE;
        name = source.getName().fullName;
        gender = source.getGender().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));
        subjects.addAll(source.getSubjects().stream()
            .map(JsonAdaptedSubject::new)
            .collect(Collectors.toList()));
        classes.addAll(source.getClasses().stream()
            .map(String::toString)
            .collect(Collectors.toList()));
        daysAttended = null;
        nextOfKin = null;
        emergencyContact = null;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Student source) {
        type = STUDENT_TYPE;
        name = source.getName().fullName;
        gender = source.getGender().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));
        subjects.addAll(source.getSubjects().stream()
            .map(JsonAdaptedSubject::new)
            .collect(Collectors.toList()));
        classes.addAll(source.getClasses().stream()
            .map(String::toString)
            .collect(Collectors.toList()));
        this.daysAttended = source.getDaysAttended().getValue();
        this.nextOfKin = source.getNextOfKinName().fullName;
        this.emergencyContact = source.getEmergencyContact().value;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public static JsonAdaptedPerson createJsonAdaptedPerson(Person source) {
        if (source.getType().equals(TEACHER_TYPE)) {
            return new JsonAdaptedPerson((Teacher) source);
        } else if (source.getType().equals(STUDENT_TYPE)) {
            return new JsonAdaptedPerson((Student) source);
        } else {
            throw new InvalidPersonTypeException();
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(personTags);

        final List<Subject> personSubjects = new ArrayList<>();
        for (JsonAdaptedSubject subject : subjects) {
            personSubjects.add(subject.toModelType());
        }
        final Set<Subject> modelSubjects = new HashSet<>(personSubjects);

        if (classes.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Class"));
        }
        final Set<String> modelClasses = new HashSet<>(classes);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

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

        final Name modelNextOfKin;
        if (nextOfKin != null) {
            if (!Name.isValidName(nextOfKin)) {
                throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
            }
            modelNextOfKin = new Name(nextOfKin);
        } else {
            modelNextOfKin = null;
        }

        final Phone modelEmergencyContact;
        if (emergencyContact != null) {
            if (!Phone.isValidPhone(emergencyContact)) {
                throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
            }
            modelEmergencyContact = new Phone(emergencyContact);
        } else {
            modelEmergencyContact = null;
        }

        if (daysAttended == null) {
            return Person.createPerson(TEACHER_TYPE, modelName, modelGender, modelPhone, modelEmail, modelAddress,
                    modelTags, modelSubjects, modelClasses, null, modelNextOfKin, modelEmergencyContact);
        }

        if (!DaysAttended.isValidDaysAttended(daysAttended)) {
            throw new IllegalValueException(DaysAttended.MESSAGE_CONSTRAINTS);
        }
        final DaysAttended modelDaysAttended = new DaysAttended(daysAttended);

        return Person.createPerson(STUDENT_TYPE, modelName, modelGender, modelPhone, modelEmail, modelAddress,
                modelTags, modelSubjects, modelClasses, modelDaysAttended, modelNextOfKin, modelEmergencyContact);
    }

}

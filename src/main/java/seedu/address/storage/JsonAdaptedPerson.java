package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.company.Industry;
import seedu.address.model.person.student.Student;
import seedu.address.model.person.student.StudentId;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String category;
    private final String phone;
    private final String email;
    private final String address;
    private final String studentID; // Specific to Student
    private final String industry; // Specific to Company
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("category") String category,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("studentID") String studentID,
                             @JsonProperty("industry") String industry,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.category = category;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.studentID = studentID;
        this.industry = industry;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        category = source instanceof Student ? "Student" : "Company";
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));

        if (source instanceof Student) {
            studentID = ((Student) source).getStudentId().value;
            industry = null; // Not applicable for Student
        } else if (source instanceof Company) {
            studentID = null; // Not applicable for Company
            industry = ((Company) source).getIndustry().value;
        } else {
            studentID = null;
            industry = null;
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

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if ("Student".equalsIgnoreCase(category)) {
            if (studentID == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        StudentId.class.getSimpleName()));
            }
            if (!StudentId.isValidId(studentID)) {
                throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
            }
            final StudentId modelStudentId = new StudentId(studentID);
            return new Student(modelName, modelStudentId, modelPhone, modelEmail, modelAddress, modelTags);
        } else if ("Company".equalsIgnoreCase(category)) {
            if (industry == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Industry.class.getSimpleName()));
            }
            if (!Industry.isValidIndustry(industry)) {
                throw new IllegalValueException(Industry.MESSAGE_CONSTRAINTS);
            }
            final Industry modelIndustry = new Industry(industry);
            return new Company(modelName, modelIndustry, modelPhone, modelEmail, modelAddress, modelTags);
        }

        throw new IllegalValueException("Unknown category for Person: " + category);
    }

}

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
import seedu.address.model.person.Interest;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.University;
import seedu.address.model.person.WorkExp;
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
    private final List<String> interests;
    private final String workExp;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String university;
    private final String major;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("workExp") String workExp,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("university") String university,
                             @JsonProperty("major") String major,
                             @JsonProperty("interests") List<String> interests) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.workExp = workExp;
        this.university = university;
        this.major = major;

        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.interests = (interests != null) ? new ArrayList<>(interests) : new ArrayList<>();
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        this.name = source.getName().fullName;
        this.phone = source.getPhone().value;
        this.email = source.getEmail().value;
        this.address = source.getAddress().value;
        this.workExp = source.getWorkExp().value;
        this.university = source.getUniversity().value;
        this.major = source.getMajor().value;

        this.tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));

        this.interests = source.getInterests().stream()
                .map(Interest::toString)
                .collect(Collectors.toList());
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

        // Validation for name
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        // Validation for phone
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        // Validation for email
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        // Validation for address
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        // Validation for work experience
        if (workExp == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, WorkExp.class.getSimpleName()));
        }
        if (!WorkExp.isValidWorkExp(workExp)) {
            throw new IllegalValueException(WorkExp.MESSAGE_CONSTRAINTS);
        }
        final WorkExp modelWorkExp = new WorkExp(workExp);

        // Validation for university
        if (university == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    University.class.getSimpleName()));
        }
        if (!University.isValidUniversity(university)) {
            throw new IllegalValueException(University.MESSAGE_CONSTRAINTS);
        }
        final University modelUniversity = new University(university);

        // Validation for major
        if (major == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Major.class.getSimpleName()));
        }
        if (!Major.isValidMajor(major)) {
            throw new IllegalValueException(Major.MESSAGE_CONSTRAINTS);
        }
        final Major modelMajor = new Major(major);

        // Correctly handle interests validation
        if (interests == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Interest.class
                    .getSimpleName()));
        }

        final List<Interest> personInterests = new ArrayList<>();
        for (String interestStr : interests) {
            if (interestStr == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Interest.class
                        .getSimpleName()));
            }
            try {
                personInterests.add(new Interest(interestStr));
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException(Interest.MESSAGE_CONSTRAINTS);
            }
        }
        final Set<Interest> modelInterests = new HashSet<>(personInterests);
        final Set<Tag> modelTags = new HashSet<>(personTags);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelWorkExp, modelTags, modelUniversity,
                modelMajor, modelInterests);
    }

}

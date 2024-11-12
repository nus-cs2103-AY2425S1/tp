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
import seedu.address.model.person.DesiredRole;
import seedu.address.model.person.Email;
import seedu.address.model.person.Experience;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Skills;
import seedu.address.model.person.Status;
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
    private final String desiredRole; // Added the DesiredRole field
    private final String skills;
    private final String experience;
    private final String status;
    private final String note;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("desiredRole") String desiredRole, // Handle DesiredRole field here
                             @JsonProperty("skills") String skills,
                             @JsonProperty("experience") String experience,
                             @JsonProperty("status") String status,
                             @JsonProperty("note") String note,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.desiredRole = desiredRole; // Initialize the DesiredRole field
        this.skills = skills;
        this.experience = experience;
        this.status = status;
        this.note = note;
        if (tags != null) {
            this.tags.addAll(tags);
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
        desiredRole = source.getDesiredRole().value; // Convert the DesiredRole to string
        skills = source.getSkills().value;
        experience = source.getExperience().value;
        status = source.getStatus().value;
        note = source.getNote().value;
        tags.addAll(source.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));
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

        // Validation for Name
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        // Validation for Phone
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        // Validation for Email
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        // Validation for Address
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        // Validation for DesiredRole
        if (desiredRole == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                DesiredRole.class.getSimpleName()));
        }
        if (!DesiredRole.isValidDesiredRole(desiredRole)) {
            throw new IllegalValueException(DesiredRole.MESSAGE_CONSTRAINTS);
        }
        final DesiredRole modelDesiredRole = new DesiredRole(desiredRole);

        // Validation for Skills
        if (skills == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Skills.class.getSimpleName()));
        }
        if (!Skills.isValidSkillsString(skills)) {
            throw new IllegalValueException(Skills.MESSAGE_CONSTRAINTS);
        }
        final Skills modelSkills = new Skills(skills);

        // Validation for Experience
        if (experience == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Experience.class.getSimpleName()));
        }
        if (!Experience.isValidExperience(experience)) {
            throw new IllegalValueException(Experience.MESSAGE_CONSTRAINTS);
        }
        final Experience modelExperience = new Experience(experience);

        // Validation for Status
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);

        // Validation for Note
        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName()));
        }
        final Note modelNote = new Note(note);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        // Return the Person object with DesiredRole
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelDesiredRole, modelSkills,
            modelExperience, modelStatus, modelNote, modelTags);
    }
}

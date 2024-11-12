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
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;

/**
 * Json-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String status;
    private final int personId;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("personId") int personId,
                             @JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                                @JsonProperty("status") String status,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {

        this.personId = personId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = status;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }
    /**
     * Converts a given {@code Person} into this class for Json use.
     */
    public JsonAdaptedPerson(Person source) {
        personId = source.getPersonId();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().emailValue;
        address = source.getAddress().value;
        status = source.getStatus().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Json-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = convertTagsToModelType();
        final Name modelName = validateAndCreateName();
        final Phone modelPhone = validateAndCreatePhone();
        final Email modelEmail = validateAndCreateEmail();
        final Address modelAddress = validateAndCreateAddress();
        final Status modelStatus = validateAndCreateStatus();
        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelStatus, modelTags, personId);
    }

    /**
     * Converts the tags of the JsonAdaptedPerson to the model's Tag object.
     *
     * @return List of Tag objects.
     */
    private List<Tag> convertTagsToModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        return personTags;
    }

    /**
     * Validates and creates the Name object.
     *
     * @return Name object.
     * @throws IllegalValueException if the name is invalid.
     */
    private Name validateAndCreateName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    /**
     * Validates and creates the Phone object.
     *
     * @return Phone object.
     * @throws IllegalValueException if the phone is invalid
     */
    private Phone validateAndCreatePhone() throws IllegalValueException {
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(phone);
    }

    /**
     * Validates and creates the Email object.
     *
     * @return Email object.
     * @throws IllegalValueException if the email is invalid
     */
    private Email validateAndCreateEmail() throws IllegalValueException {
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(email);
    }

    /**
     * Validates and creates the Address object.
     *
     * @return Address object.
     * @throws IllegalValueException if the address is invalid
     */
    private Address validateAndCreateAddress() throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(address);
    }

    /**
     * Validates and creates the Status object.
     *
     * @return Status object.
     * @throws IllegalValueException if the status is invalid
     */
    private Status validateAndCreateStatus() throws IllegalValueException {
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(status);
    }
}

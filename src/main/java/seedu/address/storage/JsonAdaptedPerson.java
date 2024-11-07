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
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email; // Optional, can be null
    private final String address; // Optional, can be null
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with all fields, with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        if (email != null) {
            this.email = email;
        } else {
            this.email = null;
        }
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Constructs a {@code JsonAdaptedPerson} without email and address, with the given person details.
     */
    public JsonAdaptedPerson(String name, String phone, List<JsonAdaptedTag> tags) {
        this(name, phone, null, null, tags);
    }

    /**
     * Constructs a {@code JsonAdaptedPerson} with either email or address.
     */
    public JsonAdaptedPerson(String name, String phone, String emailOrAddress, List<JsonAdaptedTag> tags) {
        this(name, phone,
                Email.isValidEmail(emailOrAddress) ? emailOrAddress : null,
                Address.isValidAddress(emailOrAddress) ? emailOrAddress : null,
                tags);
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().map(Email::toString).orElse(null);
        address = source.getAddress().map(Address::toString).orElse(null);
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Constructor for {@code JsonAdaptedPerson} in general
     * Returns a {@code JsonAdaptedPerson} object using the different constructors given the respective fields.
     */
    public static JsonAdaptedPerson createJsonAdaptedPerson(String name, String phone, String email, String address,
                                                            List<JsonAdaptedTag> tags) {
        if (email == null && address == null) {
            return new JsonAdaptedPerson(name, phone, tags);
        } else if (email != null && address != null) {
            return new JsonAdaptedPerson(name, phone, email, address, tags);
        } else if (email != null) {
            return new JsonAdaptedPerson(name, phone, email, tags);
        } else {
            return new JsonAdaptedPerson(name, phone, address, tags);
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

        Email modelEmail = null;
        if (email != null) {
            if (!Email.isValidEmail(email)) {
                throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
            }
            modelEmail = new Email(email);
        }

        Address modelAddress = null;
        if (address != null) {
            if (!Address.isValidAddress(address)) {
                throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
            }
            modelAddress = new Address(address);
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return Person.createPerson(modelName, modelPhone, modelEmail, modelAddress, modelTags);
    }

}

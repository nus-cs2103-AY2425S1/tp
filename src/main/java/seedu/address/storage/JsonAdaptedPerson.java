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
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.GradYear;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoomNumber;
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
    private final String roomNumber;
    private final String emergencyName;
    private final String emergencyPhone;
    private final String gradYear;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("room number") String roomNumber,
            @JsonProperty("address") String address, @JsonProperty("emergency name") String emergencyName,
            @JsonProperty("emergency phone") String emergencyPhone, @JsonProperty("graduation year") String gradYear,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.roomNumber = roomNumber;
        this.address = address;
        this.emergencyName = emergencyName;
        this.emergencyPhone = emergencyPhone;
        this.gradYear = gradYear;
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
        roomNumber = source.getRoomNumber().map(rn -> rn.value).orElse(null);
        address = source.getAddress().value;
        emergencyName = source.getEmergencyContactName().map(en -> en.fullName).orElse(null);
        emergencyPhone = source.getEmergencyContactPhone().map(ep -> ep.value).orElse(null);
        gradYear = source.getGradYear().map(gy -> gy.value).orElse(null);
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    private Name parseName(String name) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.hasValidChars(name)) {
            throw new IllegalValueException(Name.CHAR_MESSAGE_CONSTRAINTS);
        }
        if (!Name.isValidLength(name)) {
            throw new IllegalValueException(Name.LENGTH_MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    private Phone parsePhone(String phone) throws IllegalValueException {
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(phone);
    }

    private Email parseEmail(String email) throws IllegalValueException {
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(email);
    }

    private RoomNumber parseRoomNumber(String roomNumber) throws IllegalValueException {
        // room number is optional
        boolean hasRoomNumber = roomNumber != null;
        if (hasRoomNumber && !RoomNumber.isValidRoomNumber(roomNumber)) {
            throw new IllegalValueException(RoomNumber.MESSAGE_CONSTRAINTS);
        }
        return hasRoomNumber ? new RoomNumber(roomNumber) : null;
    }

    private Address parseAddress(String address) throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(address);
    }

    private EmergencyContact parseEmergencyContact(String name, String phone) throws IllegalValueException {
        final boolean hasName = name != null;
        final boolean hasPhone = phone != null;

        // emergency contact is optional
        if (!hasName && !hasPhone) {
            return null;
        }

        final Name emergencyName = hasName ? parseName(name) : null;
        final Phone emergencyPhone = hasPhone ? parsePhone(phone) : null;
        return new EmergencyContact(emergencyName, emergencyPhone);
    }

    private GradYear parseGradYear(String gradYear) throws IllegalValueException {
        // room number is optional
        boolean hasGradYear = gradYear != null;
        if (hasGradYear && !GradYear.isValidGradYear(gradYear)) {
            throw new IllegalValueException(GradYear.MESSAGE_CONSTRAINTS);
        }
        return hasGradYear ? new GradYear(gradYear) : null;
    }

    private Set<Tag> parseTags(List<JsonAdaptedTag> tags) throws IllegalValueException {
        final Set<Tag> personTags = new HashSet<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        return personTags;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final Name modelName = parseName(name);
        final Phone modelPhone = parsePhone(phone);
        final Email modelEmail = parseEmail(email);
        final RoomNumber modelRoomNumber = parseRoomNumber(roomNumber);
        final Address modelAddress = parseAddress(address);
        final EmergencyContact modelEmergencyContact = parseEmergencyContact(emergencyName, emergencyPhone);
        final GradYear modelGradYear = parseGradYear(gradYear);
        final Set<Tag> modelTags = parseTags(tags);
        return new Person(modelName, modelPhone, modelEmail, modelRoomNumber, modelAddress,
                modelEmergencyContact, modelGradYear, modelTags);
    }

}

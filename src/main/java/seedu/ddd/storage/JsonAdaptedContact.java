package seedu.ddd.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.common.Tag;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;

/**
 * Jackson-friendly version of {@link Contact}.
 * Annotation tells Jackson to check for the "type" in each JSON object to see whether it is a client or vendor
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = JsonAdaptedClient.class, name = "client"),
    @JsonSubTypes.Type(value = JsonAdaptedVendor.class, name = "vendor")
})
abstract class JsonAdaptedContact {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Contact's %s field is missing!";
    private final int id;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags;
    private final List<JsonAdaptedId> eventIds;

    /**
     * Constructs a {@code JsonAdaptedContact} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedContact(
        @JsonProperty("name") String name,
        @JsonProperty("phone") String phone,
        @JsonProperty("email") String email,
        @JsonProperty("address") String address,
        @JsonProperty("tags") List<JsonAdaptedTag> tags,
        @JsonProperty("id") int id,
        @JsonProperty("eventIds") List<JsonAdaptedId> eventIds
    ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = new ArrayList<>(tags);
        this.eventIds = new ArrayList<>(eventIds);
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedContact(Contact source) {
        id = source.getId().id;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags = new ArrayList<>();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        eventIds = new ArrayList<>();
        eventIds.addAll(source.getEventIds().stream()
                .map(JsonAdaptedId::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted contact object into the model's {@code Contact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact.
     */
    public Contact toModelType() throws IllegalValueException {
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

        if (!Id.isValidId(id)) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
        }
        final Id modelContactId = new Id(id);

        return createContact(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelContactId);
    }

    /**
     * Retrieves the eventIds of the contact
     * @throws IllegalValueException if there were any data constraints violated in the adapted eventId.
     */
    public Set<Id> getEventIds() throws IllegalValueException {
        final List<Id> personEventIds = new ArrayList<>();
        for (JsonAdaptedId eventId : eventIds) {
            personEventIds.add(eventId.toModelType());
        }
        return new HashSet<>(personEventIds);
    }

    public abstract Contact createContact(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
            Id contactId) throws IllegalValueException;
}

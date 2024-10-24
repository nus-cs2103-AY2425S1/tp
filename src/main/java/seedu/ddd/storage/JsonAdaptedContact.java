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
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Name;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.event.common.EventId;
import seedu.ddd.model.tag.Tag;

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
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedEventId> eventIds = new ArrayList<>();

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
        @JsonProperty("eventIds") List<JsonAdaptedEventId> eventIds
    ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (eventIds != null) {
            this.eventIds.addAll(eventIds);
        }
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedContact(Contact source) {
        id = source.getId().contactId;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        eventIds.addAll(source.getEventIds().stream()
                .map(JsonAdaptedEventId::new)
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

        if (!ContactId.isValidContactId(id)) {
            throw new IllegalValueException(ContactId.MESSAGE_CONSTRAINTS);
        }
        final ContactId modelContactId = new ContactId(id);

        return createContact(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelContactId);
    }

    /**
     * Retrieves the eventIds of the contact
     * @throws IllegalValueException if there were any data constraints violated in the adapted eventId.
     */
    public Set<EventId> getEventIds() throws IllegalValueException {
        final List<EventId> personEventIds = new ArrayList<>();
        for (JsonAdaptedEventId eventId : eventIds) {
            personEventIds.add(eventId.toModelType());
        }
        return new HashSet<>(personEventIds);
    }

    public abstract Contact createContact(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
            ContactId contactId) throws IllegalValueException;
}

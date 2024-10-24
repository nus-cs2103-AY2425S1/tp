package seedu.ddd.storage;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.client.Date;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Name;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.tag.Tag;

class JsonAdaptedClient extends JsonAdaptedContact {
    private final String date;
    /**
     * Constructs a {@code JsonAdaptedClient} with the given client's details.
     */
    @JsonCreator
    public JsonAdaptedClient(
        @JsonProperty("name") String name,
        @JsonProperty("phone") String phone,
        @JsonProperty("email") String email,
        @JsonProperty("address") String address,
        @JsonProperty("date") String date,
        @JsonProperty("tags") List<JsonAdaptedTag> tags,
        @JsonProperty("id") int id,
        @JsonProperty("eventIds") List<JsonAdaptedEventId> eventIds
    ) {
        super(name, phone, email, address, tags, id, eventIds);
        this.date = date;
    }

    /**
     * Converts a given {@code Client} into this class for Jackson use.
     */
    public JsonAdaptedClient(Client source) {
        super(source);
        this.date = source.getDate().toString();
    }

    @Override
    public Client createContact(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
        ContactId contactId) throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);
        return new Client(name, phone, email, address, modelDate, tags, contactId);
    }
}

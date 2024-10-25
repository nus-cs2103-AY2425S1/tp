package seedu.ddd.storage;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Name;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.tag.Tag;

class JsonAdaptedClient extends JsonAdaptedContact {

    /**
     * Constructs a {@code JsonAdaptedClient} with the given client's details.
     */
    @JsonCreator
    public JsonAdaptedClient(
        @JsonProperty("name") String name,
        @JsonProperty("phone") String phone,
        @JsonProperty("email") String email,
        @JsonProperty("address") String address,
        @JsonProperty("tags") List<JsonAdaptedTag> tags,
        @JsonProperty("id") int id,
        @JsonProperty("eventIds") List<JsonAdaptedEventId> eventIds
    ) {
        super(name, phone, email, address, tags, id, eventIds);
    }

    /**
     * Converts a given {@code Client} into this class for Jackson use.
     */
    public JsonAdaptedClient(Client source) {
        super(source);
    }

    @Override
    public Client createContact(
        Name name,
        Phone phone,
        Email email,
        Address address,
        Set<Tag> tags,
        ContactId contactId
    ) throws IllegalValueException {
        return new Client(name, phone, email, address, tags, contactId);
    }
}

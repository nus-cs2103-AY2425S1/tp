package seedu.ddd.storage;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.client.Date;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Id;
import seedu.ddd.model.contact.common.Name;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.tag.Tag;

class JsonAdaptedClient extends JsonAdaptedContact {
    private final String date;
    /**
     * Constructs a {@code JsonAdaptedClient} with the given client's details.
     */
    @JsonCreator
    public JsonAdaptedClient(@JsonProperty("id") int id, @JsonProperty("name") String name,
        @JsonProperty("phone") String phone, @JsonProperty("email") String email,
        @JsonProperty("address") String address, @JsonProperty("tags") List<JsonAdaptedTag> tags,
        @JsonProperty("date") String date) {
        super(id, name, phone, email, address, tags);
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
    public Client createContact(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Id id)
            throws IllegalValueException {
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);
        return new Client(name, phone, email, address, modelDate, tags, id);
    }
}

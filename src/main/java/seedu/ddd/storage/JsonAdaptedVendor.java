package seedu.ddd.storage;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Name;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.contact.vendor.Service;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.tag.Tag;

class JsonAdaptedVendor extends JsonAdaptedContact {
    private final String service;

    /**
     * Constructs a {@code JsonAdaptedVendor} with the given vendor's details.
     */
    @JsonCreator
    public JsonAdaptedVendor(
        @JsonProperty("name") String name,
        @JsonProperty("phone") String phone,
        @JsonProperty("email") String email,
        @JsonProperty("address") String address,
        @JsonProperty("service") String service,
        @JsonProperty("tags") List<JsonAdaptedTag> tags,
        @JsonProperty("id") int id,
        @JsonProperty("eventIds") List<JsonAdaptedEventId> eventIds
    ) {
        super(name, phone, email, address, tags, id, eventIds);
        this.service = service;
    }

    /**
     * Converts a given {@code Vendor} into this class for Jackson use.
     */
    public JsonAdaptedVendor(Vendor source) {
        super(source);
        this.service = source.getService().toString();
    }

    @Override
    public Vendor createContact(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
            ContactId contactId) throws IllegalValueException {
        if (service == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Service.class.getSimpleName()));
        }
        if (!Service.isValidService(service)) {
            throw new IllegalValueException(Service.MESSAGE_CONSTRAINTS);
        }
        final Service modelService = new Service(service);
        return new Vendor(name, phone, email, address, modelService, tags, contactId);
    }
}

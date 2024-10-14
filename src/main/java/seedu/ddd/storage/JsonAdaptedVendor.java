package seedu.ddd.storage;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Id;
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
    public JsonAdaptedVendor(@JsonProperty("id") int id, @JsonProperty("name") String name,
        @JsonProperty("phone") String phone, @JsonProperty("email") String email,
        @JsonProperty("address") String address, @JsonProperty("tags") List<JsonAdaptedTag> tags,
        @JsonProperty("service") String service) {
        super(id, name, phone, email, address, tags);
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
    public Vendor createContact(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Id id)
            throws IllegalValueException {
        if (!Service.isValidService(service)) {
            throw new IllegalValueException(Service.MESSAGE_CONSTRAINTS);
        }
        final Service modelService = new Service(service);
        return new Vendor(name, phone, email, address, modelService, tags, id);
    }
}

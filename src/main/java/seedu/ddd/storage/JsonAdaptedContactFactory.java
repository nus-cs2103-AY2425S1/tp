package seedu.ddd.storage;

import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Vendor;

class JsonAdaptedContactFactory {
    public static JsonAdaptedContact create(Contact contact) {
        if (contact instanceof Client) {
            return new JsonAdaptedClient((Client) contact);
        } else if (contact instanceof Vendor) {
            return new JsonAdaptedVendor((Vendor) contact);
        } else {
            throw new IllegalArgumentException("Unknown contact type: " + contact.getClass());
        }
    }
}

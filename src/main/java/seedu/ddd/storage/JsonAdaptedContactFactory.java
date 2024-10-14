package seedu.ddd.storage;

import seedu.ddd.model.person.Client;
import seedu.ddd.model.person.Contact;
import seedu.ddd.model.person.Vendor;

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

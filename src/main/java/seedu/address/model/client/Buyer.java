package seedu.address.model.client;

/**
 * Represents a Buyer in the address book.
 */
public class Buyer extends Client {

    /**
     * Constructs a Buyer object with the specified name, phone, and email.
     *
     * @param name  The name of the buyer.
     * @param phone The phone number of the buyer.
     * @param email The email address of the buyer.
     */
    public Buyer(Name name, Phone phone, Email email) {
        super(name, phone, email);
    }
}

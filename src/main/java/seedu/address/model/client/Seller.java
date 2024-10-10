package seedu.address.model.client;

/**
 * Represents a Seller in the address book.
 */
public class Seller extends Client {

    /**
     * Constructs a Seller object with the specified name, phone, and email.
     *
     * @param name  The name of the seller.
     * @param phone The phone number of the seller.
     * @param email The email address of the seller.
     */
    public Seller(Name name, Phone phone, Email email) {
        super(name, phone, email);
    }
}

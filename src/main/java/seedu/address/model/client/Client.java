package seedu.address.model.client;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Client in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Client {

    // Identity fields
    protected final NameWithoutNumber name;
    protected final Phone phone;
    protected final Email email;

    /**
     * Every field must be present and not null.
     */
    public Client(NameWithoutNumber name, Phone phone, Email email) {
        requireAllNonNull(name, phone, email);
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public NameWithoutNumber getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public abstract boolean isSameClient(Client otherClient);
    public abstract boolean isDuplicateEmail(Client otherClient);
    public abstract boolean isBuyer();
    public abstract boolean isSeller();
    public abstract String getTypeString();
}

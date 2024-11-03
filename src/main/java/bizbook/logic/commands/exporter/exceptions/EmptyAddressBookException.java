package bizbook.logic.commands.exporter.exceptions;

/**
 * Represents an error that is caused because the {@code AddressBook} is empty.
 */
public class EmptyAddressBookException extends RuntimeException {
    /**
     * Constructs a new {@code EmptyAddressBookException} with the specified detail {@code message} and {@code cause}.
     */
    public EmptyAddressBookException() {
        super();
    }
}

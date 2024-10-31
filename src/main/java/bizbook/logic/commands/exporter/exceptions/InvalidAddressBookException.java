package bizbook.logic.commands.exporter.exceptions;

/**
 * Represents an error that is caused because something is wrong with the {@link bizbook.model.AddressBook}.
 */
public class InvalidAddressBookException extends RuntimeException {
    /**
     * Constructs a new {@code InvalidAddressBookException} with the specified detail {@code message}.
     */
    public InvalidAddressBookException(String message) {
        super(message);
    }
}

package bizbook.logic.commands.exporter;

import java.io.IOException;
import java.nio.file.Path;

import bizbook.logic.commands.exporter.exceptions.InvalidAddressBookException;
import bizbook.model.ReadOnlyAddressBook;

/**
 * Represents a class that can export an address book to a particular format
 */
public interface Exporter {
    public static final String MESSAGE_EMPTY_ADDRESS_BOOK = "Address book is empty. Consider adding a person first.";

    /**
     * Exports the address book
     */
    public void exportAddressBook(ReadOnlyAddressBook addressBook) throws IOException, InvalidAddressBookException;

    /**
     * Returns the path that the addressbook will be exported to
     */
    public Path getExportPath();
}

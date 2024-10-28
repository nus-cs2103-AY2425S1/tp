package seedu.address.logic.commands.exporter;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.logic.commands.exporter.exceptions.EmptyAddressBookException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a class that can export an address book to a particular format
 */
public interface Exporter {
    /**
     * Exports the address book
     */
    public void exportAddressBook(ReadOnlyAddressBook addressBook) throws IOException, EmptyAddressBookException;

    /**
     * Returns the path that the addressbook will be exported to
     */
    public Path getExportPath();
}

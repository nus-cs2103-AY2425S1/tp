package seedu.address.logic.exporter;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a class that can export an address book to a particular format
 */
public interface Exporter {
    /**
     * Exports the address book
     */
    public void exportAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Returns the path that the addressbook will be exported to
     */
    public Path getExportPath();
}

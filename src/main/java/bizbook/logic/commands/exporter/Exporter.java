package bizbook.logic.commands.exporter;

import java.io.IOException;
import java.nio.file.Path;

import bizbook.logic.commands.exporter.exceptions.EmptyAddressBookException;
import bizbook.model.ReadOnlyAddressBook;

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

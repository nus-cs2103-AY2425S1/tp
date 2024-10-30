package bizbook.logic.commands.exporter;

import java.io.IOException;
import java.nio.file.Path;

import bizbook.logic.commands.exporter.exceptions.EmptyAddressBookException;
import bizbook.model.AddressBook;

/**
 * Represents a class that can import an address book from a particular format
 */
public interface Importer {
    /**
     * Imports an address book
     */
    public AddressBook importAddressBook(Path filePath) throws IOException, EmptyAddressBookException;
}

package bizbook.logic.commands.exporter;

import java.io.IOException;
import java.nio.file.Path;

import bizbook.logic.commands.exporter.exceptions.InvalidFileException;
import bizbook.model.AddressBook;

/**
 * Represents a class that can import an address book from a particular format
 */
public interface Importer {
    public static final String MESSAGE_EMPTY_FILE = "There are no people to import from the file.";
    public static final String MESSAGE_INVALID_FORMAT = "File is not in the proper format or the file is corrupted.";
    /**
     * Imports an address book
     */
    public AddressBook importAddressBook(Path filePath) throws IOException, InvalidFileException;
}

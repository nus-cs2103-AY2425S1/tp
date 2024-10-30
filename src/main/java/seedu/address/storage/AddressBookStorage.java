package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file to save data to.
     */
    Path getAddressBookSaveFilePath();

    /**
     * Returns the file path of the data file to export data to.
     */
    Path getAddressBookExportFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    /**
     * @see #getAddressBookSaveFilePath()
     */
    Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyAddressBook)
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;

    /**
     * Exports the given {@link ReadOnlyAddressBook} into a .csv file.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void exportAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #exportAddressBook(ReadOnlyAddressBook)
     */
    void exportAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;

}

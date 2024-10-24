package seedu.address.storage;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyReminderAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.ReminderAddressBook}.
 */
public interface ReminderAddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getReminderAddressBookFilePath();

    /**
     * Returns ReminderAddressBook data as a {@link ReadOnlyReminderAddressBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyReminderAddressBook> readReminderAddressBook() throws DataLoadingException;

    /**
     * @see #getReminderAddressBookFilePath()
     */
    Optional<ReadOnlyReminderAddressBook> readReminderAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyReminderAddressBook} to the storage.
     * @param reminderAddressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveReminderAddressBook(ReadOnlyReminderAddressBook reminderAddressBook) throws IOException;

    /**
     * @see #saveReminderAddressBook(ReadOnlyReminderAddressBook)
     */
    void saveReminderAddressBook(ReadOnlyReminderAddressBook reminderAddressBook, Path filePath) throws IOException;

}

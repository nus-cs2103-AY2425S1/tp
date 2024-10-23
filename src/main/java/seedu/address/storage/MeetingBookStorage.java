package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyMeetingBook;

/**
 * Represents a storage for {@link seedu.address.model.MeetingBook}.
 */
public interface MeetingBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMeetingBookFilePath();

    /**
     * Returns MeetingBook data as a {@link ReadOnlyMeetingBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyMeetingBook> readMeetingBook() throws DataLoadingException;

    /**
     * @see #getMeetingBookFilePath()
     */
    Optional<ReadOnlyMeetingBook> readMeetingBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyMeetingBook} to the storage.
     * @param meetingBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMeetingBook(ReadOnlyMeetingBook meetingBook) throws IOException;

    /**
     * @see #saveMeetingBook(ReadOnlyMeetingBook)
     */
    void saveMeetingBook(ReadOnlyMeetingBook meetingBook, Path filePath) throws IOException;

}

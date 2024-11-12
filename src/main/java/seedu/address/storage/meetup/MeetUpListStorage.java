package seedu.address.storage.meetup;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyMeetUpList;

/**
 * Represents a storage for {@link seedu.address.model.meetup}.
 */
public interface MeetUpListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMeetUpListFilePath();

    /**
     * Returns MeetUp data as a {@link ReadOnlyMeetUpList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyMeetUpList> readMeetUpList() throws DataLoadingException;

    /**
     * @see #getMeetUpListFilePath()
     */
    Optional<ReadOnlyMeetUpList> readMeetUpList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyMeetUpList} to the storage.
     * @param meetUpList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMeetUpList(ReadOnlyMeetUpList meetUpList) throws IOException;

    /**
     * @see #saveMeetUpList(ReadOnlyMeetUpList)
     */
    void saveMeetUpList(ReadOnlyMeetUpList meetUp, Path filePath) throws IOException;

}

package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyScheduleList;

public interface ScheduleStorage {
    
    /**
     * Returns the file path of the data file.
     */
    Path getScheduleListFilePath();
    
    /**
     * Returns Schedule data as a {@link ReadOnlyScheduleList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyScheduleList> readScheduleList() throws DataLoadingException;
    
    /**
     * @see #getScheduleListFilePath()
     */
    Optional<ReadOnlyScheduleList> readScheduleList(Path filePath) throws DataLoadingException;
    
    /**
     * Saves the given {@link ReadOnlyScheduleList to the storage.
     * @param scheduleList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveScheduleList(ReadOnlyScheduleList scheduleList) throws IOException;
    
    /**
     * @see #saveScheduleList(ReadOnlyScheduleList)
     */
    void saveScheduleList(ReadOnlyScheduleList scheduleList, Path filePath) throws IOException;
    
}
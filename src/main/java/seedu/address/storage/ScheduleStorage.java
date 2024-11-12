package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyScheduleList;

/**
 * Represents a storage for {@link seedu.address.model.ScheduleList}.
 * This interface defines the operations for reading from and writing to
 * the schedule storage file.
 */
public interface ScheduleStorage {

    /**
     * Returns the file path of the schedule list data file.
     *
     * @return The {@code Path} of the schedule list data file.
     */
    Path getScheduleListFilePath();

    /**
     * Reads and returns the schedule data as a {@link ReadOnlyScheduleList}.
     * If the storage file is not found, it returns {@code Optional.empty()}.
     *
     * @return An {@code Optional<ReadOnlyScheduleList>} representing the schedule list.
     * @throws DataLoadingException If there is an issue loading the data from storage.
     */
    Optional<ReadOnlyScheduleList> readScheduleList() throws DataLoadingException;

    /**
     * Reads and returns the schedule data from the specified file path.
     * Returns {@code Optional.empty()} if the storage file is not found.
     *
     * @param filePath The path to the schedule data file. Cannot be null.
     * @return An {@code Optional<ReadOnlyScheduleList>} representing the schedule list.
     * @throws DataLoadingException If there is an issue loading the data from storage.
     */
    Optional<ReadOnlyScheduleList> readScheduleList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyScheduleList} to the storage.
     *
     * @param scheduleList The schedule list to save. Cannot be null.
     * @throws IOException If there is an issue writing to the file.
     */
    void saveScheduleList(ReadOnlyScheduleList scheduleList) throws IOException;

    /**
     * Saves the given {@link ReadOnlyScheduleList} to the storage at the specified file path.
     *
     * @param scheduleList The schedule list to save. Cannot be null.
     * @param filePath     The path to the schedule data file. Cannot be null.
     * @throws IOException If there is an issue writing to the file.
     */
    void saveScheduleList(ReadOnlyScheduleList scheduleList, Path filePath) throws IOException;

    /**
     * Handles the case when the schedule data file is corrupted.
     */
    void handleCorruptedFile();
}

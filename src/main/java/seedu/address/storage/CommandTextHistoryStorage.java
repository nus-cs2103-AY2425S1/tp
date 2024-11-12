package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.CommandTextHistory;

/**
 * Represents a storage for {@link seedu.address.model.CommandTextHistory}.
 */
public interface CommandTextHistoryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCommandTextHistoryFilePath();

    /**
     * Returns CommandTextHistory data as a {@link CommandTextHistory}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<CommandTextHistory> readCommandTextHistory() throws DataLoadingException;

    /**
     * @see #getCommandTextHistoryFilePath()
     */
    Optional<CommandTextHistory> readCommandTextHistory(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link CommandTextHistory} to the storage.
     * @param commandTextHistory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCommandTextHistory(CommandTextHistory commandTextHistory) throws IOException;

    /**
     * @see #saveCommandTextHistory(CommandTextHistory)
     */
    void saveCommandTextHistory(CommandTextHistory commandTextHistory, Path filePath) throws IOException;

}

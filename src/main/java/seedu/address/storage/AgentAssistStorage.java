package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AgentAssist;
import seedu.address.model.ReadOnlyAgentAssist;

/**
 * Represents a storage for {@link AgentAssist}.
 */
public interface AgentAssistStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAgentAssistFilePath();

    /**
     * Returns AgentAssist data as a {@link ReadOnlyAgentAssist}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyAgentAssist> readAgentAssist() throws DataLoadingException;

    /**
     * @see #getAgentAssistFilePath()
     */
    Optional<ReadOnlyAgentAssist> readAgentAssist(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyAgentAssist} to the storage.
     * @param agentAssist cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAgentAssist(ReadOnlyAgentAssist agentAssist) throws IOException;

    /**
     * @see #saveAgentAssist(ReadOnlyAgentAssist)
     */
    void saveAgentAssist(ReadOnlyAgentAssist agentAssist, Path filePath) throws IOException;

}

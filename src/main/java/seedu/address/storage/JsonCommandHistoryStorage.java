package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.CommandHistory;
import seedu.address.model.ReadOnlyCommandHistory;

/**
 * A class to access CommandHistory stored in the hard disk as a json file
 */
public class JsonCommandHistoryStorage implements CommandHistoryStorage {

    private Path filePath;

    public JsonCommandHistoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getCommandHistoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<CommandHistory> readCommandHistory() throws DataLoadingException {
        return readCommandHistory(filePath);
    }

    /**
     * Similar to {@link #readCommandHistory()}
     * @param commandHistoryFilePath location of the data. Cannot be null.
     * @throws DataLoadingException if the file format is not as expected.
     */
    public Optional<CommandHistory> readCommandHistory(Path commandHistoryFilePath) throws DataLoadingException {
        return JsonUtil.readJsonFile(commandHistoryFilePath, CommandHistory.class);
    }

    @Override
    public void saveCommandHistory(ReadOnlyCommandHistory commandHistory) throws IOException {
        JsonUtil.saveJsonFile(commandHistory, filePath);
    }

}

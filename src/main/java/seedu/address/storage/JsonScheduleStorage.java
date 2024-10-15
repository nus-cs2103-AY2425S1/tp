package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyScheduleList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;


public class JsonScheduleStorage implements ScheduleStorage{
    
    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);
    
    private Path filePath;
    
    public JsonScheduleStorage(Path filePath) {
        this.filePath = filePath;
    }
    public Path getScheduleListFilePath() {
        return filePath;
    }
    
    @Override
    public Optional<ReadOnlyScheduleList> readScheduleList() throws DataLoadingException {
        return readScheduleList(filePath);
    }
    
    /**
     * Similar to {@link #readScheduleList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyScheduleList> readScheduleList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);
        
        Optional<JsonSerializableScheduleStorage> jsonScheduleList = JsonUtil.readJsonFile(
                filePath, JsonSerializableScheduleStorage.class);
        if (!jsonScheduleList.isPresent()) {
            return Optional.empty();
        }
        
        try {
            return Optional.of(jsonScheduleList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }
    
    @Override
    public void saveScheduleList(ReadOnlyScheduleList scheduleList) throws IOException {
        saveScheduleList(scheduleList, filePath);
    }
    
    /**
     * Similar to {@link #saveScheduleList(ReadOnlyScheduleList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveScheduleList(ReadOnlyScheduleList scheduleList, Path filePath) throws IOException {
        requireNonNull(scheduleList);
        requireNonNull(filePath);
        
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableScheduleStorage(scheduleList), filePath);
    }
    
}

package seedu.address.storage.meetup;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyMeetUpList;

/**
 * A class to access MeetUpList data stored as a json file on the hard disk.
 */
public class JsonMeetUpListStorage implements MeetUpListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMeetUpListStorage.class);

    private Path filePath;

    public JsonMeetUpListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getMeetUpListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMeetUpList> readMeetUpList() throws DataLoadingException {
        return readMeetUpList(filePath);
    }

    /**
     * Similar to {@link #readMeetUpList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyMeetUpList> readMeetUpList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableMeetUpList> jsonMeetUpList = JsonUtil.readJsonFile(
                filePath, JsonSerializableMeetUpList.class);
        if (!jsonMeetUpList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonMeetUpList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveMeetUpList(ReadOnlyMeetUpList meetUpList) throws IOException {
        saveMeetUpList(meetUpList, filePath);
    }

    /**
     * Similar to {@link #saveMeetUpList(ReadOnlyMeetUpList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMeetUpList(ReadOnlyMeetUpList meetUpList, Path filePath) throws IOException {
        requireNonNull(meetUpList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        logger.info("saving meetup list");
        JsonUtil.saveJsonFile(new JsonSerializableMeetUpList(meetUpList), filePath);
    }

}

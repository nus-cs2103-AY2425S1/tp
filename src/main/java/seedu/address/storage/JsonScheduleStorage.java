package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyScheduleList;

/**
 * A class to access the schedule data stored as a JSON file on the hard disk.
 */
public class JsonScheduleStorage implements ScheduleStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    /**
     * Constructs a {@code JsonScheduleStorage} with the specified file path.
     *
     * @param filePath Path to the JSON file where the schedule data is stored.
     */
    public JsonScheduleStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path where the schedule data is stored.
     *
     * @return The {@code Path} to the schedule list data file.
     */
    public Path getScheduleListFilePath() {
        return filePath;
    }

    /**
     * Reads the schedule list from the file specified in the constructor.
     *
     * @return An {@code Optional<ReadOnlyScheduleList>} containing the schedule list if it exists.
     * @throws DataLoadingException if there is an error reading the file or the data is invalid.
     */
    @Override
    public Optional<ReadOnlyScheduleList> readScheduleList() throws DataLoadingException {
        return readScheduleList(filePath);
    }

    /**
     * Reads the schedule list from a specified file path.
     * Similar to {@link #readScheduleList()}.
     *
     * @param filePath The location of the schedule data file. Cannot be null.
     * @return An {@code Optional<ReadOnlyScheduleList>} containing the schedule list if it exists.
     * @throws DataLoadingException if there is an error reading the file or the data is invalid.
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

    /**
     * Saves the schedule list to the file specified in the constructor.
     *
     * @param scheduleList The schedule list to save. Cannot be null.
     * @throws IOException if there is an error writing to the file.
     */
    @Override
    public void saveScheduleList(ReadOnlyScheduleList scheduleList) throws IOException {
        saveScheduleList(scheduleList, filePath);
    }

    /**
     * Saves the schedule list to a specified file path.
     * Similar to {@link #saveScheduleList(ReadOnlyScheduleList)}.
     *
     * @param scheduleList The schedule list to save. Cannot be null.
     * @param filePath     The location of the data file. Cannot be null.
     * @throws IOException if there is an error writing to the file.
     */
    public void saveScheduleList(ReadOnlyScheduleList scheduleList, Path filePath) throws IOException {
        requireNonNull(scheduleList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableScheduleStorage(scheduleList), filePath);
    }

    @Override
    public void handleCorruptedFile() {
        try {
            FileUtil.copyFile(this.filePath,
                    Path.of(this.filePath.toString() + "." + new Random().nextInt() + ".bak"));
            logger.info("Corrupted schedule.json file backed up");
        } catch (IOException e) {
            logger.warning("unable to back up corrupted schedule file");
        }
    }
}

package seedu.address.storage;

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
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;

/**
 * A class to access Appointment data stored as a json file on the hard disk.
 */
public class JsonAppointmentBookStorage implements AppointmentBookStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonAppointmentBookStorage.class);

    private Path filePath;

    public JsonAppointmentBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAppointmentBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAppointmentBook> readAppointmentBook(
            ReadOnlyAddressBook addressBook) throws DataLoadingException {
        return readAppointmentBook(filePath, addressBook);
    }

    /**
     * Similar to {@link #readAppointmentBook(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyAppointmentBook> readAppointmentBook(
            Path filePath,
            ReadOnlyAddressBook addressBook) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAppointmentBook> jsonAppointmentBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAppointmentBook.class);
        if (!jsonAppointmentBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAppointmentBook.get().toModelType(addressBook));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook) throws IOException {
        saveAppointmentBook(appointmentBook, filePath);
    }

    /**
     * Similar to {@link #saveAppointmentBook(ReadOnlyAppointmentBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook, Path filePath) throws IOException {
        requireNonNull(appointmentBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAppointmentBook(appointmentBook), filePath);
    }
}

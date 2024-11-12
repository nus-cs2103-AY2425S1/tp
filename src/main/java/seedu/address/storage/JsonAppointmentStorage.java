package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.appointment.Appointment;

/**
 * A class to access Appointment data stored as a JSON file on the hard disk.
 */
public class JsonAppointmentStorage implements AppointmentStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAppointmentStorage.class);

    private final Path filePath;

    public JsonAppointmentStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getAppointmentFilePath() {
        return filePath;
    }

    @Override
    public Optional<List<Appointment>> readAppointments() throws DataLoadingException {
        return readAppointments(filePath);
    }

    /**
     * Similar to {@link #readAppointments()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    @Override
    public Optional<List<Appointment>> readAppointments(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAppointment> jsonAppointment = JsonUtil.readJsonFile(
                filePath, JsonSerializableAppointment.class);
        if (jsonAppointment.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAppointment.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAppointments(List<Appointment> appointments) throws IOException {
        saveAppointments(appointments, filePath);
    }

    /**
     * Similar to {@link #saveAppointments(List)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveAppointments(List<Appointment> appointments, Path filePath) throws IOException {
        requireNonNull(appointments);
        requireNonNull(filePath);

        List<JsonAdaptedAppointment> jsonAdaptedAppointments =
                appointments.stream().map(JsonAdaptedAppointment::new).toList();

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAppointment(jsonAdaptedAppointments), filePath);
    }
}

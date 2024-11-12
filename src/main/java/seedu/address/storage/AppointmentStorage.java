package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.appointment.Appointment;

/**
 * Represents a storage for {@link Appointment}.
 */
public interface AppointmentStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAppointmentFilePath();

    /**
     * Returns Appointment data as a {@code List<Appointment>}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<List<Appointment>> readAppointments() throws DataLoadingException;

    /**
     * @see #readAppointments()
     */
    Optional<List<Appointment>> readAppointments(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@code List<Appointment>} to the storage.
     * @param appointments cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAppointments(List<Appointment> appointments) throws IOException;

    /**
     * @see #saveAppointments(List)
     */
    void saveAppointments(List<Appointment> appointments, Path filePath) throws IOException;
}

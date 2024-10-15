package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAppointmentBook;

/**
 * Represents a storage for {@link seedu.address.model.appointment.Appointment}.
 */
public interface AppointmentBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAppointmentBookFilePath();

    /**
     * Returns Appointment data as a {@link ReadOnlyAppointmentBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyAppointmentBook> readAppointmentBook() throws DataLoadingException;

    /**
     * @see #getAppointmentBookFilePath()
     */
    Optional<ReadOnlyAppointmentBook> readAppointmentBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyAppointmentBook} to the storage.
     * @param appointmentBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook) throws IOException;

    /**
     * @see #saveAppointmentBook(ReadOnlyAppointmentBook)
     */
    void saveAppointmentBook(ReadOnlyAppointmentBook addressBook, Path filePath) throws IOException;

}

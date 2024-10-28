package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyClientBook;
import seedu.address.model.ReadOnlyMeetingBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage, PropertyBookStorage,
        ClientBookStorage, MeetingBookStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Optional<ReadOnlyClientBook> readClientBook() throws DataLoadingException;

    @Override
    void saveClientBook(ReadOnlyClientBook clientBook) throws IOException;

    @Override
    void savePropertyBook(ReadOnlyPropertyBook propertyBook) throws IOException;

    @Override
    Optional<ReadOnlyPropertyBook> readPropertyBook() throws DataLoadingException;

    @Override
    Optional<ReadOnlyMeetingBook> readMeetingBook() throws DataLoadingException;

    @Override
    void saveMeetingBook(ReadOnlyMeetingBook meetingBook) throws IOException;

}

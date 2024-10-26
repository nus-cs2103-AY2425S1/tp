package seedu.edulog.storage;

import java.nio.file.Path;
import java.util.Optional;

import seedu.edulog.commons.exceptions.DataLoadingException;
import seedu.edulog.model.ReadOnlyGiftList;

/**
 * Represents a storage for {@link GiftList}.
 */
public interface GiftListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getGiftListFilePath();

    /**
     * Returns GiftList data as a {@link ReadOnlyGiftList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyGiftList> readGiftList() throws DataLoadingException;

    /**
     * @see #getGiftListFilePath()
     */
    Optional<ReadOnlyGiftList> readGiftList(Path filePath) throws DataLoadingException;

}

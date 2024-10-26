package seedu.edulog.storage;

import java.io.IOException;
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

    /**
     * Saves the given {@link ReadOnlyGiftList} to the storage.
     * @param giftList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGiftList(ReadOnlyGiftList giftList) throws IOException;

    /**
     * @see #saveGiftList(ReadOnlyGiftList)
     */
    void saveGiftList(ReadOnlyGiftList giftList, Path filePath) throws IOException;

}

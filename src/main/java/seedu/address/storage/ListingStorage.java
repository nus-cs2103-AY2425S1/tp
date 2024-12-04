package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyListings;

/**
 * Represents a storage for {@link seedu.address.model.Listings}.
 */
public interface ListingStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getListingsFilePath();

    /**
     * Returns Listings data as a {@link ReadOnlyListings}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyListings> readListings() throws DataLoadingException;

    /**
     * @see #getListingsFilePath()
     */
    Optional<ReadOnlyListings> readListings(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyListings} to the storage.
     * @param listings cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveListings(ReadOnlyListings listings) throws IOException;

    /**
     * @see #saveListings(ReadOnlyListings)
     */
    void saveListings(ReadOnlyListings listings, Path filePath) throws IOException;
}

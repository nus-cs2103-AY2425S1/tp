package seedu.address.storage.buyer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyBuyerList;

/**
 * Represents a storage for {@link seedu.address.model.BuyerList}.
 */
public interface BuyerListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBuyerListFilePath();

    /**
     * Returns BuyerList data as a {@link ReadOnlyBuyerList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyBuyerList> readBuyerList() throws DataLoadingException;

    /**
     * @see #getBuyerListFilePath()
     */
    Optional<ReadOnlyBuyerList> readBuyerList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyBuyerList} to the storage.
     * @param buyerList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBuyerList(ReadOnlyBuyerList buyerList) throws IOException;

    /**
     * @see #saveBuyerList(ReadOnlyBuyerList)
     */
    void saveBuyerList(ReadOnlyBuyerList buyerList, Path filePath) throws IOException;

}

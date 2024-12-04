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
import seedu.address.model.ReadOnlyListings;

/**
 * A class to access Listings data stored as a json file on the hard disk.
 */
public class JsonListingsStorage implements ListingStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonListingsStorage.class);

    private Path filePath;
    public JsonListingsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getListingsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyListings> readListings() throws DataLoadingException {
        return readListings(filePath);
    }

    /**
     * Similar to {@link #readListings()} ()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyListings> readListings(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableListings> jsonListings = JsonUtil.readJsonFile(
                filePath, JsonSerializableListings.class);
        if (!jsonListings.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonListings.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveListings(ReadOnlyListings listings) throws IOException {
        saveListings(listings, filePath);
    }

    /**
     * Similar to {@link #saveListings(ReadOnlyListings)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveListings(ReadOnlyListings listings, Path filePath) throws IOException {
        requireNonNull(listings);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableListings(listings), filePath);
    }

}

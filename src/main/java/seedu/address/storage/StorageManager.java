package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyBuyerList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.buyer.BuyerListStorage;
import seedu.address.storage.meetup.MeetUpListStorage;

/**
 * Manages storage of BuyerList data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private BuyerListStorage buyerListStorage;
    private UserPrefsStorage userPrefsStorage;
    private MeetUpListStorage meetUpListStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code BuyerListStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(BuyerListStorage buyerListStorage, UserPrefsStorage userPrefsStorage,
            MeetUpListStorage meetUpListStorage) {
        this.buyerListStorage = buyerListStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.meetUpListStorage = meetUpListStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ BuyerList methods ==============================

    @Override
    public Path getBuyerListFilePath() {
        return buyerListStorage.getBuyerListFilePath();
    }

    @Override
    public Optional<ReadOnlyBuyerList> readBuyerList() throws DataLoadingException {
        return readBuyerList(buyerListStorage.getBuyerListFilePath());
    }

    @Override
    public Optional<ReadOnlyBuyerList> readBuyerList(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return buyerListStorage.readBuyerList(filePath);
    }

    @Override
    public void saveBuyerList(ReadOnlyBuyerList buyerList) throws IOException {
        saveBuyerList(buyerList, buyerListStorage.getBuyerListFilePath());
    }

    @Override
    public void saveBuyerList(ReadOnlyBuyerList buyerList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        buyerListStorage.saveBuyerList(buyerList, filePath);
    }

    // ================ MeetUp methods ==============================

    @Override
    public Path getMeetUpListFilePath() {
        return meetUpListStorage.getMeetUpListFilePath();
    }

    @Override
    public Optional<ReadOnlyMeetUpList> readMeetUpList() throws DataLoadingException {
        return readMeetUpList(meetUpListStorage.getMeetUpListFilePath());
    }

    @Override
    public Optional<ReadOnlyMeetUpList> readMeetUpList(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return meetUpListStorage.readMeetUpList(filePath);
    }

    @Override
    public void saveMeetUpList(ReadOnlyMeetUpList meetUpList) throws IOException {
        saveMeetUpList(meetUpList, meetUpListStorage.getMeetUpListFilePath());
    }

    @Override
    public void saveMeetUpList(ReadOnlyMeetUpList meetUpList, Path filePath) throws IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        meetUpListStorage.saveMeetUpList(meetUpList, filePath);
    }
}

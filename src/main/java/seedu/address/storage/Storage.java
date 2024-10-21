package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyBuyerList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.ReadOnlyPropertyList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.buyer.BuyerListStorage;
import seedu.address.storage.meetup.MeetUpListStorage;
import seedu.address.storage.property.PropertyListStorage;

/**
 * API of the Storage component
 */
public interface Storage extends BuyerListStorage, UserPrefsStorage, MeetUpListStorage, PropertyListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    // buyer ================================================================

    @Override
    Path getBuyerListFilePath();

    @Override
    Optional<ReadOnlyBuyerList> readBuyerList() throws DataLoadingException;

    @Override
    void saveBuyerList(ReadOnlyBuyerList buyerList) throws IOException;

    // meetUp ================================================================

    @Override
    Path getMeetUpListFilePath();

    @Override
    Optional<ReadOnlyMeetUpList> readMeetUpList() throws DataLoadingException;

    @Override
    void saveMeetUpList(ReadOnlyMeetUpList meetUpList) throws IOException;

    // property ================================================================

    @Override
    Path getPropertyListFilePath();

    @Override
    Optional<ReadOnlyPropertyList> readPropertyList() throws DataLoadingException;

    @Override
    void savePropertyList(ReadOnlyPropertyList propertyList) throws IOException;
}

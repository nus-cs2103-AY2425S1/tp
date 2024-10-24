package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.property.Property;

/**
 * Represents the in-memory model of the buyer list data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final BuyerList buyerList;
    private final MeetUpList meetUpList;
    private final PropertyList propertyList;
    private final UserPrefs userPrefs;
    private final FilteredList<Buyer> filteredBuyers;
    private final FilteredList<MeetUp> filteredMeetUps;
    private final FilteredList<Property> filteredProperties;

    /**
     * Initializes a ModelManager with the given buyerList, meetUpList, propertyList and userPrefs.
     */
    public ModelManager(ReadOnlyBuyerList buyerList, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyMeetUpList meetUpList, ReadOnlyPropertyList propertyList) {
        requireAllNonNull(buyerList, userPrefs, meetUpList, propertyList);

        logger.info("Initializing with buyer list: " + buyerList + " and user prefs " + userPrefs
                + " and meet up list " + meetUpList + " and property list " + propertyList);

        this.buyerList = new BuyerList(buyerList);
        this.userPrefs = new UserPrefs(userPrefs);
        this.meetUpList = new MeetUpList(meetUpList);
        this.propertyList = new PropertyList(propertyList);
        filteredBuyers = new FilteredList<>(this.buyerList.getBuyerList());
        filteredMeetUps = new FilteredList<>(this.meetUpList.getMeetUpList());
        filteredProperties = new FilteredList<>(this.propertyList.getPropertyList());

    }

    public ModelManager() {
        this(new BuyerList(), new UserPrefs(), new MeetUpList(), new PropertyList());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return buyerList.equals(otherModelManager.buyerList)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredBuyers.equals(otherModelManager.filteredBuyers)
                && filteredMeetUps.equals(otherModelManager.filteredMeetUps)
                && filteredProperties.equals(otherModelManager.filteredProperties);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getBuyerListFilePath() {
        return userPrefs.getBuyerListFilePath();
    }

    @Override
    public void setBuyerListFilePath(Path buyerListFilePath) {
        requireNonNull(buyerListFilePath);
        userPrefs.setBuyerListFilePath(buyerListFilePath);
    }

    @Override
    public Path getMeetUpListFilePath() {
        return userPrefs.getMeetUpListFilePath();
    }

    @Override
    public void setMeetUpListFilePath(Path meetUpListFilePath) {
        requireNonNull(meetUpListFilePath);
        userPrefs.setMeetUpListFilePath(meetUpListFilePath);
    }

    @Override
    public Path getPropertyListFilePath() {
        return userPrefs.getPropertyListFilePath();
    }

    @Override
    public void setPropertyListFilePath(Path propertyListFilePath) {
        requireNonNull(propertyListFilePath);
        userPrefs.setPropertyListFilePath(propertyListFilePath);
    }

    //=========== BuyerList ================================================================================

    @Override
    public void setBuyerList(ReadOnlyBuyerList buyerList) {
        this.buyerList.resetData(buyerList);
    }

    @Override
    public ReadOnlyBuyerList getBuyerList() {
        return buyerList;
    }

    @Override
    public boolean hasBuyer(Buyer buyer) {
        requireNonNull(buyer);
        return buyerList.hasBuyer(buyer);
    }

    @Override
    public void deleteBuyer(Buyer target) {
        buyerList.removeBuyer(target);
    }

    @Override
    public void addBuyer(Buyer buyer) {
        buyerList.addBuyer(buyer);
        updateFilteredBuyerList(PREDICATE_SHOW_ALL_BUYERS);
    }

    @Override
    public void setBuyer(Buyer target, Buyer editedBuyer) {
        requireAllNonNull(target, editedBuyer);

        buyerList.setBuyer(target, editedBuyer);
    }

    //=========== Filtered Buyer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Buyer} backed by the internal list of
     * {@code versionedBuyerList}
     */
    @Override
    public ObservableList<Buyer> getFilteredBuyerList() {
        return filteredBuyers;
    }

    @Override
    public void updateFilteredBuyerList(Predicate<Buyer> predicate) {
        requireNonNull(predicate);
        filteredBuyers.setPredicate(predicate);
    }

    //=========== MeetUp List ================================================================================

    @Override
    public void setMeetUpList(ReadOnlyMeetUpList meetUpList) {
        this.meetUpList.resetData(meetUpList);
    }

    @Override
    public ReadOnlyMeetUpList getMeetUpList() {
        return meetUpList;
    }

    @Override
    public boolean hasMeetUp(MeetUp meetUp) {
        requireNonNull(meetUp);
        return meetUpList.hasMeetUp(meetUp);
    }

    @Override
    public void deleteMeetUp(MeetUp target) {
        meetUpList.removeMeetUp(target);
    }

    @Override
    public void addMeetUp(MeetUp meetUp) {
        meetUpList.addMeetUp(meetUp);
        updateFilteredMeetUpList(PREDICATE_SHOW_ALL_MEETUPS);
    }

    @Override
    public void setMeetUp(MeetUp target, MeetUp editedMeetUp) {
        requireAllNonNull(target, editedMeetUp);

        meetUpList.setMeetUp(target, editedMeetUp);
    }

    //=========== Filtered MeetUp List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Buyer} backed by the internal list of
     * {@code versionedBuyerList}
     */
    @Override
    public ObservableList<MeetUp> getFilteredMeetUpList() {
        return filteredMeetUps;
    }

    @Override
    public void updateFilteredMeetUpList(Predicate <MeetUp> predicate) {
        requireNonNull(predicate);
        filteredMeetUps.setPredicate(predicate);
    }

    //=========== Property List ================================================================================
    @Override
    public void setPropertyList(ReadOnlyPropertyList propertyList) {
        this.propertyList.resetData(propertyList);
    }

    @Override
    public ReadOnlyPropertyList getPropertyList() {
        return propertyList;
    }

    @Override
    public boolean hasProperty(Property property) {
        requireNonNull(property);
        return propertyList.hasProperty(property);
    }

    @Override
    public void deleteProperty(Property property) {
        propertyList.removeProperty(property);
    }

    @Override
    public void addProperty(Property property) {
        propertyList.addProperty(property);
        updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
    }

    @Override
    public void setProperty(Property target, Property editedProperty) {
        requireAllNonNull(target, editedProperty);
        propertyList.setProperty(target, editedProperty);
    }

    //=========== Filtered Property List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Buyer} backed by the internal list of
     * {@code versionedBuyerList}
     */
    @Override
    public ObservableList<Property> getFilteredPropertyList() {
        return filteredProperties;
    }

    @Override
    public void updateFilteredPropertyList(Predicate<Property> predicate) {
        requireNonNull(predicate);
        filteredProperties.setPredicate(predicate);
    }
}

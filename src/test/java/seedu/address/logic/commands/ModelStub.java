package seedu.address.logic.commands;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBuyerList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.ReadOnlyPropertyList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.property.Property;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getBuyerListFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setBuyerListFilePath(Path buyerListFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addBuyer(Buyer buyer) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setBuyerList(ReadOnlyBuyerList newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyBuyerList getBuyerList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasBuyer(Buyer buyer) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteBuyer(Buyer target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setBuyer(Buyer target, Buyer editedBuyer) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Buyer> getUnfilteredBuyerList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Buyer> getFilteredBuyerList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredBuyerList(Predicate<Buyer> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getMeetUpListFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setMeetUpListFilePath(Path meetUpListFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setMeetUpList(ReadOnlyMeetUpList meetUpList) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyMeetUpList getMeetUpList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<MeetUp> getUnfilteredMeetUpList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<MeetUp> getFilteredMeetUpList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addMeetUp(MeetUp meetUp) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setMeetUp(MeetUp target, MeetUp editedMeetUp) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteMeetUp(MeetUp target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredMeetUpList(Predicate<MeetUp> meetUp) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasMeetUp(MeetUp meetUp) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getPropertyListFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPropertyListFilePath(Path propertyListFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPropertyList(ReadOnlyPropertyList propertyList) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addProperty(Property property) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyPropertyList getPropertyList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasProperty(Property property) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteProperty(Property property) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setProperty(Property property, Property editedProperty) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Property> getUnfilteredPropertyList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Property> getFilteredPropertyList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPropertyList(Predicate<Property> predicate) {
        throw new AssertionError("This method should not be called.");
    }
}

package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.property.TypicalProperties.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PropertyList;
import seedu.address.model.ReadOnlyBuyerList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.ReadOnlyPropertyList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.property.Property;
import seedu.address.testutil.property.PropertyBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_propertyAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPropertyAdded modelStub = new ModelStubAcceptingPropertyAdded();
        Property validProperty = new PropertyBuilder().build();

        CommandResult commandResult = new AddCommand(validProperty).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validProperty)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validProperty), modelStub.propertiesAdded);
    }

    public void execute_duplicateProperty_throwsCommandException() {
        Property validProperty = new PropertyBuilder().build();
        AddCommand addCommand = new AddCommand(validProperty);
        ModelStub modelStub = new ModelStubWithProperty(validProperty);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_PROPERTY, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Property alice = new PropertyBuilder().withLandlordName("Alice").build();
        Property bob = new PropertyBuilder().withLandlordName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different property -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that has all of its methods failing.
     */
    private class ModelStub implements Model {
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

        // ============================ MeetUp Model  ==========================

        @Override
        public ReadOnlyMeetUpList getMeetUpList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<MeetUp> getFilteredMeetUpList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMeetUp(MeetUp meetUp) {

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
            return false;
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
        public ObservableList<Property> getFilteredPropertyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPropertyList(Predicate<Property> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single property.
     */
    private class ModelStubWithProperty extends ModelStub {
        private final Property property;

        ModelStubWithProperty(Property property) {
            requireNonNull(property);
            this.property = property;
        }

        @Override
        public boolean hasProperty(Property property) {
            requireNonNull(property);
            return this.property.isSameProperty(property);
        }
    }

    /**
     * A Model stub that always accepts the property being added.
     */
    private class ModelStubAcceptingPropertyAdded extends ModelStub {
        final ArrayList<Property> propertiesAdded = new ArrayList<>();

        @Override
        public boolean hasProperty(Property property) {
            requireNonNull(property);
            return propertiesAdded.stream().anyMatch(property::isSameProperty);
        }

        @Override
        public void addProperty(Property property) {
            requireNonNull(property);
            propertiesAdded.add(property);
        }

        @Override
        public ReadOnlyPropertyList getPropertyList() {
            return new PropertyList();
        }
    }
}

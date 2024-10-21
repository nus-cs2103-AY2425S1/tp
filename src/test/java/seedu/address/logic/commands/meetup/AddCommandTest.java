package seedu.address.logic.commands.meetup;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.meetup.TypicalMeetUps.FIRST_MEETUP;

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
import seedu.address.model.MeetUpList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBuyerList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.ReadOnlyPropertyList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.property.Property;
import seedu.address.testutil.meetup.MeetUpBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullMeetUp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_meetUpAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMeetUpAdded modelStub = new ModelStubAcceptingMeetUpAdded();
        MeetUp validMeetUp = new MeetUpBuilder().build();

        CommandResult commandResult = new AddCommand(validMeetUp).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validMeetUp)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMeetUp), modelStub.meetUpsAdded);
    }

    @Test
    public void execute_duplicateMeetUp_throwsCommandException() {
        MeetUp validMeetUp = new MeetUpBuilder().build();
        AddCommand addCommand = new AddCommand(validMeetUp);

        ModelStub modelStub = new ModelStubWithMeetUp(validMeetUp);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_MEETUP, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        MeetUp meetUpA = new MeetUpBuilder().withName("meetUpA").build();
        MeetUp meetUpB = new MeetUpBuilder().withName("meetUpB").build();
        AddCommand addMeetUpACommand = new AddCommand(meetUpA);
        AddCommand addMeetUpBCommand = new AddCommand(meetUpB);

        // same object -> returns true
        assertTrue(addMeetUpACommand.equals(addMeetUpACommand));

        // same values -> returns true
        AddCommand addMeetUpACommandCopy = new AddCommand(meetUpA);
        assertTrue(addMeetUpACommand.equals(addMeetUpACommandCopy));

        // different types -> returns false
        assertFalse(addMeetUpACommand.equals(1));

        // null -> returns false
        assertFalse(addMeetUpACommand.equals(null));

        // different buyer -> returns false
        assertFalse(addMeetUpACommand.equals(addMeetUpBCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(FIRST_MEETUP);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + FIRST_MEETUP + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
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
        public ObservableList<Property> getFilteredPropertyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPropertyList(Predicate<Property> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single buyer.
     */
    private class ModelStubWithMeetUp extends ModelStub {
        private final MeetUp meetUp;

        ModelStubWithMeetUp(MeetUp meetUp) {
            requireNonNull(meetUp);
            this.meetUp = meetUp;
        }

        @Override
        public boolean hasMeetUp(MeetUp meetUp) {
            requireNonNull(meetUp);
            return this.meetUp.isSameMeetUp(meetUp);
        }
    }

    /**
     * A Model stub that always accept the meetup being added.
     */
    private class ModelStubAcceptingMeetUpAdded extends ModelStub {
        final ArrayList<MeetUp> meetUpsAdded = new ArrayList<>();

        @Override
        public boolean hasMeetUp(MeetUp meetUp) {
            requireNonNull(meetUp);
            return meetUpsAdded.stream().anyMatch(meetUp::isSameMeetUp);
        }

        @Override
        public void addMeetUp(MeetUp meetUp) {
            requireNonNull(meetUp);
            meetUpsAdded.add(meetUp);
        }

        @Override
        public ReadOnlyMeetUpList getMeetUpList() {
            return new MeetUpList();
        }
    }

}

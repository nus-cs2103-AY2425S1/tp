package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.exceptions.NotAssignedException;
import seedu.address.model.exceptions.VolunteerDeleteMissingDateException;
import seedu.address.model.exceptions.VolunteerDuplicateDateException;
import seedu.address.model.volunteer.UniqueVolunteerList;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalVolunteers;
import seedu.address.testutil.VolunteerBuilder;

public class UnassignCommandTest {

    private Index indexOne = Index.fromOneBased(1);
    private Index indexTwo = Index.fromOneBased(2); // Out of bounds index

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignCommand(null, indexOne));
    }

    @Test
    public void equals_twoIdenticalCommands_returnTrue() {
        UnassignCommand unassignCommand = new UnassignCommand(indexOne, indexOne);
        UnassignCommand unassignCommandCopy = new UnassignCommand(indexOne, indexOne);
        assertTrue(unassignCommand.equals(unassignCommand));
        assertTrue(unassignCommand.equals(unassignCommandCopy));
    }

    @Test
    public void equals_twoDifferentCommands_returnFalse() {
        UnassignCommand unassignCommand1 = new UnassignCommand(indexOne, indexOne);
        UnassignCommand unassignCommand2 = new UnassignCommand(indexOne, indexTwo);
        assertFalse(unassignCommand1.equals(unassignCommand2));
    }

    @Test
    public void equals_differentCommandType_returnFalse() {
        AssignCommand assignCommand = new AssignCommand(indexOne, indexOne);
        UnassignCommand unassignCommand = new UnassignCommand(indexOne, indexOne);
        assertFalse(assignCommand.equals(unassignCommand));
    }

    @Test
    public void execute_validUnassignCommand_success() {
        UnassignCommand unassignCommand = new UnassignCommand(indexOne, indexOne);
        assertDoesNotThrow(() -> unassignCommand.execute(new ModelStubWithOneVolunteerAssignedToEvent()));
    }

    @Test
    public void execute_outOfBoundsIndex_failure() {
        assertThrows(CommandException.class, () -> new UnassignCommand(indexOne, indexTwo)
                .execute(new ModelStubWithOneVolunteerAssignedToEvent()));
        assertThrows(CommandException.class, () -> new UnassignCommand(indexTwo, indexOne)
                .execute(new ModelStubWithOneVolunteerAssignedToEvent()));
    }

    @Test
    public void execute_volunteerNotAssigned_failure() {
        UnassignCommand unassignCommand = new UnassignCommand(indexOne, indexOne);
        assertThrows(Exception.class, () -> unassignCommand.execute(new ModelStubWithVolunteerNotAssignedToEvent()));
    }

    @Test
    public void execute_noVolunteers_failure() {
        UnassignCommand unassignCommand = new UnassignCommand(indexOne, indexOne);
        assertThrows(CommandException.class, () -> unassignCommand.execute(new ModelStubWithOneEventOnly()));
    }

    @Test
    public void execute_noEvents_failure() {
        UnassignCommand unassignCommand = new UnassignCommand(indexOne, indexOne);
        assertThrows(CommandException.class, () -> unassignCommand.execute(new ModelStubWithOneVolunteerOnly()));
    }

    @Test
    public void execute_noEventsAndVolunteers_failure() {
        UnassignCommand unassignCommand = new UnassignCommand(indexOne, indexOne);
        assertThrows(CommandException.class, () -> unassignCommand.execute(new ModelStubEmpty()));
    }

    /**
     * A default model stub that have all the methods failing.
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasVolunteer(Volunteer volunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteVolunteer(Volunteer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetDisplayLists() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addVolunteer(Volunteer volunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVolunteer(Volunteer volunteer, Volunteer editedVolunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Volunteer> getFilteredVolunteerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public void updateFilteredVolunteerList(Predicate<Volunteer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Volunteer getVolunteer(int volunteerId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Event getEvent(int eventId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean filterEventsByName(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void viewEvent(Event eventToView) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void viewVolunteer(Volunteer volunteerToView) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void assignVolunteerToEvent(Volunteer volunteer, Event event) throws CommandException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unassignVolunteerFromEvent(Volunteer volunteer, Event event) throws CommandException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDatesToVolunteer(Volunteer volunteerToAddDate, String dateList) throws
                VolunteerDuplicateDateException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeDatesFromVolunteer(Volunteer volunteerToRemoveDate, String dateList) throws
                VolunteerDeleteMissingDateException {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean filterVolunteersByName(Predicate<Volunteer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void filterEvent(Event eventToView) {
            throw new AssertionError("This method should not be called.");
        }
    }

    private abstract class UnassignTestModelStub extends ModelStub {
        protected final UniqueVolunteerList volunteers = new UniqueVolunteerList();
        protected final UniqueEventList events = new UniqueEventList();

        @Override
        public void unassignVolunteerFromEvent(Volunteer volunteer, Event event) throws NotAssignedException {
            if (!volunteer.isInvolvedInEvent(event.getName().toString())) {
                throw new NotAssignedException();
            }
            if (!event.hasVolunteer(volunteer.getName().toString())) {
                throw new NotAssignedException();
            }
            volunteer.removeEvent(event.getName().toString());
            event.unassignVolunteer(volunteer.getName().toString());
        }

        @Override
        public FilteredList<Volunteer> getFilteredVolunteerList() {
            return new FilteredList<>(volunteers.asUnmodifiableObservableList());
        }

        @Override
        public FilteredList<Event> getFilteredEventList() {
            return new FilteredList<>(events.asUnmodifiableObservableList());
        }
    }

    private class ModelStubWithVolunteerNotAssignedToEvent extends UnassignTestModelStub {

        public ModelStubWithVolunteerNotAssignedToEvent() {
            Volunteer volunteer = new VolunteerBuilder()
                    .withAvailableDate(TypicalEvents.EVENT_A.getDate().toParsableString())
                    .build();
            volunteers.add(volunteer);
            events.add(TypicalEvents.EVENT_A);
        }
    }

    private class ModelStubWithOneVolunteerAssignedToEvent extends UnassignTestModelStub {
        public ModelStubWithOneVolunteerAssignedToEvent() {
            Volunteer volunteer = new VolunteerBuilder()
                    .withAvailableDate(TypicalEvents.EVENT_A.getDate().toParsableString())
                    .build();
            Event event = new EventBuilder()
                    .withDate(TypicalEvents.EVENT_A.getDate().toParsableString())
                    .withVolunteers(volunteer.getName().toString())
                    .build();
            volunteer.addEvent(event.getName().toString());
            volunteers.add(volunteer);
            events.add(event);
        }
    }

    private class ModelStubWithOneVolunteerOnly extends UnassignTestModelStub {
        public ModelStubWithOneVolunteerOnly() {
            volunteers.add(TypicalVolunteers.ALICE);
        }
    }

    private class ModelStubWithOneEventOnly extends UnassignTestModelStub {
        public ModelStubWithOneEventOnly() {
            events.add(TypicalEvents.EVENT_A);
        }
    }

    private class ModelStubEmpty extends UnassignTestModelStub {

    }

    private class ModelStubVolunteerAssignedToOverlappingEvent extends UnassignTestModelStub {
        public ModelStubVolunteerAssignedToOverlappingEvent() {
            Volunteer volunteer = new VolunteerBuilder()
                    .withAvailableDate(TypicalEvents.EVENT_A.getDate().toParsableString())
                    .build();
            Event event = new EventBuilder()
                    .withDate(TypicalEvents.EVENT_A.getDate().toParsableString())
                    .withStartTime(TypicalEvents.EVENT_A.getStartTime().toString())
                    .withEndTime(TypicalEvents.EVENT_A.getEndTime().toString())
                    .build();

            volunteer.addEvent(event.getName().toString());
            volunteers.add(volunteer);
            events.add(event);
            events.add(TypicalEvents.EVENT_A);
        }
    }
}

package seedu.address.logic.commands.volunteercommands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.exceptions.VolunteerDeleteMissingDateException;
import seedu.address.model.exceptions.VolunteerDuplicateDateException;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.VolunteerBuilder;

public class VolunteerNewCommandTest {

    @Test
    public void constructor_nullVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new VolunteerNewCommand(null));
    }

    @Test
    public void execute_volunteerAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingVolunteerAdded modelStub = new ModelStubAcceptingVolunteerAdded();
        Volunteer validVolunteer = new VolunteerBuilder().build();

        CommandResult commandResult = new VolunteerNewCommand(validVolunteer).execute(modelStub);

        assertEquals(String.format(VolunteerNewCommand.MESSAGE_SUCCESS, Messages.format(validVolunteer)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validVolunteer), modelStub.volunteersAdded);
    }

    @Test
    public void execute_duplicateVolunteer_throwsCommandException() {
        Volunteer validVolunteer = new VolunteerBuilder().build();
        VolunteerNewCommand volunteerNewCommand = new VolunteerNewCommand(validVolunteer);
        ModelStub modelStub = new ModelStubWithVolunteer(validVolunteer);

        assertThrows(CommandException.class, VolunteerNewCommand.MESSAGE_DUPLICATE_VOLUNTEER, () ->
                volunteerNewCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Volunteer alice = new VolunteerBuilder().withName("Alice").build();
        Volunteer bob = new VolunteerBuilder().withName("Bob").build();
        VolunteerNewCommand addAliceCommand = new VolunteerNewCommand(alice);
        VolunteerNewCommand addBobCommand = new VolunteerNewCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        VolunteerNewCommand addAliceCommandCopy = new VolunteerNewCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different volunteer -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that has all methods throwing AssertionErrors by default.
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
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
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
            throw new AssertionError("This method should not be called.");
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
        public void viewEvent(Event eventToView) {
            throw new AssertionError("This method should not be called.");
        }
        public ObservableList<Event> filterEventsByName(String searchString) {
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
        public void removeDatesFromVolunteer(Volunteer volunteerToRemoveDate, String dateList)
                throws VolunteerDeleteMissingDateException {
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
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Volunteer> filterVolunteersByName(String searchString) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A model stub that contains a single volunteer.
     */
    private class ModelStubWithVolunteer extends ModelStub {
        private final Volunteer volunteer;

        ModelStubWithVolunteer(Volunteer volunteer) {
            requireNonNull(volunteer);
            this.volunteer = volunteer;
        }

        @Override
        public boolean hasVolunteer(Volunteer volunteer) {
            requireNonNull(volunteer);
            return this.volunteer.isSameVolunteer(volunteer);
        }
    }

    /**
     * A model stub that always accepts the volunteer being added.
     */
    private class ModelStubAcceptingVolunteerAdded extends ModelStub {
        final ArrayList<Volunteer> volunteersAdded = new ArrayList<>();

        @Override
        public boolean hasVolunteer(Volunteer volunteer) {
            requireNonNull(volunteer);
            return volunteersAdded.stream().anyMatch(volunteer::isSameVolunteer);
        }

        @Override
        public void addVolunteer(Volunteer volunteer) {
            requireNonNull(volunteer);
            volunteersAdded.add(volunteer);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}

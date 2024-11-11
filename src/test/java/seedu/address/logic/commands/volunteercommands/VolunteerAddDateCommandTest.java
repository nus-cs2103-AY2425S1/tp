package seedu.address.logic.commands.volunteercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.exceptions.VolunteerDeleteMissingDateException;
import seedu.address.model.exceptions.VolunteerDuplicateDateException;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.VolunteerBuilder;

public class VolunteerAddDateCommandTest {

    @Test
    public void constructor_validInputs_initialisesCorrectly() {
        VolunteerAddDateCommand command = new VolunteerAddDateCommand(Index.fromOneBased(1), "2022-01-12");
        assertEquals(Index.fromOneBased(1), command.targetIndex);
        assertEquals("2022-01-12", command.dateList);
    }

    @Test
    public void execute_addDates_successful() throws Exception {
        ModelStubAcceptingDateAdded modelStub = new ModelStubAcceptingDateAdded();
        Volunteer validVolunteer = new VolunteerBuilder().withName("John Doe").build();
        modelStub.volunteersAdded.add(validVolunteer);

        VolunteerAddDateCommand command = new VolunteerAddDateCommand(Index.fromOneBased(1), "2022-01-12");
        CommandResult result = command.execute(modelStub);

        assertEquals(String.format("Added dates to %s's list of available dates.", validVolunteer.getName()),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateDate_throwsCommandException() {
        ModelStubWithVolunteer modelStub = new ModelStubWithVolunteer();
        Volunteer validVolunteer = new VolunteerBuilder().withName("Jane Doe").build();
        modelStub.volunteersAdded.add(validVolunteer);

        VolunteerAddDateCommand command = new VolunteerAddDateCommand(Index.fromOneBased(1), "2022-01-12");
        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubWithVolunteer modelStub = new ModelStubWithVolunteer();
        VolunteerAddDateCommand command = new VolunteerAddDateCommand(Index.fromOneBased(2), "2022-01-12");

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX, () ->
                command.execute(modelStub));
    }

    @Test
    public void equals() {
        VolunteerAddDateCommand addDateCommand1 = new VolunteerAddDateCommand(Index.fromOneBased(1), "2022-01-12");
        VolunteerAddDateCommand addDateCommand2 = new VolunteerAddDateCommand(Index.fromOneBased(1), "2022-01-12");
        VolunteerAddDateCommand addDateCommand3 = new VolunteerAddDateCommand(Index.fromOneBased(2), "2022-01-12");

        assertTrue(addDateCommand1.equals(addDateCommand1)); // same object
        assertTrue(addDateCommand1.equals(addDateCommand2)); // same values
        assertFalse(addDateCommand1.equals(addDateCommand3)); // different index
        assertFalse(addDateCommand1.equals(null)); // null
        assertFalse(addDateCommand1.equals(1)); // different type
    }

    @Test
    public void toString_correctFormat() {
        Index targetIndex = Index.fromOneBased(1);
        VolunteerAddDateCommand command = new VolunteerAddDateCommand(targetIndex, "2022-01-12");

        String expectedString = VolunteerAddDateCommand.class.getCanonicalName() + "{targetIndex="
                + targetIndex.toString() + "}";


        assertEquals(expectedString, command.toString());
    }



    /**
     * A model stub that contains a volunteer and allows adding dates without throwing exceptions.
     */
    private class ModelStubAcceptingDateAdded extends ModelStub {
        final ObservableList<Volunteer> volunteersAdded = FXCollections.observableArrayList();

        @Override
        public ObservableList<Volunteer> getFilteredVolunteerList() {
            return volunteersAdded;
        }

        @Override
        public void addDatesToVolunteer(Volunteer volunteerToAddDate, String dateList)
                throws VolunteerDuplicateDateException {
            volunteerToAddDate.addAvailableDates(dateList); // Assuming Volunteer has addDates method
        }
    }

    /**
     * A model stub that contains a single volunteer and throws VolunteerDuplicateDateException.
     */
    private class ModelStubWithVolunteer extends ModelStubAcceptingDateAdded {
        @Override
        public void addDatesToVolunteer(Volunteer volunteerToAddDate, String dateList)
                throws VolunteerDuplicateDateException {
            throw new VolunteerDuplicateDateException("Duplicate date found.");
        }
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
        public boolean filterEventsByName(Predicate<Event> predicate) {
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
        public void filterEvent(Event eventToView) {
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
        public boolean filterVolunteersByName(Predicate<Volunteer> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.TimeClashException;
import seedu.address.testutil.PersonBuilder;

public class ScheduleCommandTest {
    private Index index = Index.fromZeroBased(0);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private LocalDateTime startTime = LocalDateTime.parse("30-07-2024 11:00", formatter);
    private LocalDateTime endTime = LocalDateTime.parse("30-07-2024 12:00", formatter);
    private String location = "A Valid Location";
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(null, startTime, endTime, location));
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(index, null, endTime, location));
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(index, startTime, null, location));
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(index, startTime, endTime, null));
    }

    @Test
    public void execute_meetingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMeetingAdded modelStub = new ScheduleCommandTest.ModelStubAcceptingMeetingAdded();
        Person person = new PersonBuilder().build();
        Meeting validMeeting = new Meeting(person.getName(), startTime, endTime, location);;

        CommandResult commandResult = new ScheduleCommand(index, startTime, endTime, location).execute(modelStub);

        assertEquals(String.format(ScheduleCommand.MESSAGE_SUCCESS, person.getName(), Messages.format(validMeeting)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMeeting), modelStub.meetingsAdded);
    }

    @Test
    public void execute_meetingClash_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        LocalDateTime startTime = LocalDateTime.of(2024, 10, 9, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 10, 9, 10, 0);

        try {
            Meeting existingMeeting = new Meeting(validPerson.getName(), startTime, endTime, "Location A");

            ModelStubWithMeeting modelStub = new ModelStubWithMeeting(validPerson, existingMeeting);

            LocalDateTime newStartTime = LocalDateTime.of(2024, 10, 9, 9, 30);
            LocalDateTime newEndTime = LocalDateTime.of(2024, 10, 9, 10, 30);
            ScheduleCommand scheduleCommand = new ScheduleCommand(Index.fromZeroBased(0), newStartTime,
                    newEndTime, "Location B");

            assertThrows(CommandException.class, "Meeting times overlap", () -> scheduleCommand.execute(modelStub));
        } catch (TimeClashException | CommandException e) {
            // This should not happen in the setup phase
            throw new RuntimeException(e);
        }
    }


    /**
     * A default model stub that have all methods failing.
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
        public void addPerson(Person person) {
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
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMeeting(Person target, Meeting meeting) throws CommandException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String listMeetings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getMeetingSize() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMeeting(Person target, Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Meeting getMeeting(int index) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person and meeting under the person.
     */
    private class ModelStubWithMeeting extends ModelStub {
        private final Person person;
        private final ArrayList<Meeting> meetings = new ArrayList<>();

        ModelStubWithMeeting(Person person, Meeting meeting) {
            requireNonNull(person);
            requireNonNull(meeting);
            this.person = person;
            this.meetings.add(meeting);
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }

        @Override
        public void addMeeting(Person person, Meeting meeting) throws CommandException {
            requireNonNull(meeting);
            for (Meeting existingMeeting : meetings) {
                if (existingMeeting.isOverlap(meeting)) {
                    throw new CommandException("Meeting times overlap");
                }
            }
            meetings.add(meeting);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableList(Arrays.asList(person));
        }
    }

    /**
     * A Model stub that always accept the Meeting being added.
     */
    private class ModelStubAcceptingMeetingAdded extends ModelStub {
        private ArrayList<Meeting> meetingsAdded = new ArrayList<>();
        private ArrayList<Person> personsAdded = new ArrayList<>();
        private Person person = new PersonBuilder().build();


        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            personsAdded.add(person);
            return javafx.collections.FXCollections.observableList(personsAdded);
        }

        @Override
        public void addMeeting(Person person, Meeting meeting) {
            requireNonNull(meeting);
            requireNonNull(person);
            meetingsAdded.add(meeting);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}

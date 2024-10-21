package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMeetUps.FIRST_MEETUP;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.MeetUpList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.person.Person;
import seedu.address.testutil.MeetUpBuilder;

public class AddMeetUpCommandTest {

    @Test
    public void constructor_nullMeetUp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMeetUpCommand(null));
    }

    @Test
    public void execute_meetUpAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMeetUpAdded modelStub = new ModelStubAcceptingMeetUpAdded();
        MeetUp validMeetUp = new MeetUpBuilder().build();

        CommandResult commandResult = new AddMeetUpCommand(validMeetUp).execute(modelStub);

        assertEquals(String.format(AddMeetUpCommand.MESSAGE_SUCCESS, Messages.format(validMeetUp)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMeetUp), modelStub.meetUpsAdded);
    }

    @Test
    public void execute_duplicateMeetUp_throwsCommandException() {
        MeetUp validMeetUp = new MeetUpBuilder().build();
        AddMeetUpCommand addMeetUpCommand = new AddMeetUpCommand(validMeetUp);

        ModelStub modelStub = new ModelStubWithMeetUp(validMeetUp);

        assertThrows(CommandException.class,
                AddMeetUpCommand.MESSAGE_DUPLICATE_MEETUP, () -> addMeetUpCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        MeetUp meetUpA = new MeetUpBuilder().withName("meetUpA").build();
        MeetUp meetUpB = new MeetUpBuilder().withName("meetUpB").build();
        AddMeetUpCommand addMeetUpACommand = new AddMeetUpCommand(meetUpA);
        AddMeetUpCommand addMeetUpBCommand = new AddMeetUpCommand(meetUpB);

        // same object -> returns true
        assertTrue(addMeetUpACommand.equals(addMeetUpACommand));

        // same values -> returns true
        AddMeetUpCommand addMeetUpACommandCopy = new AddMeetUpCommand(meetUpA);
        assertTrue(addMeetUpACommand.equals(addMeetUpACommandCopy));

        // different types -> returns false
        assertFalse(addMeetUpACommand.equals(1));

        // null -> returns false
        assertFalse(addMeetUpACommand.equals(null));

        // different person -> returns false
        assertFalse(addMeetUpACommand.equals(addMeetUpBCommand));
    }

    @Test
    public void toStringMethod() {
        AddMeetUpCommand addMeetUpCommand = new AddMeetUpCommand(FIRST_MEETUP);
        String expected = AddMeetUpCommand.class.getCanonicalName() + "{toAdd=" + FIRST_MEETUP + "}";
        assertEquals(expected, addMeetUpCommand.toString());
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
    }

    /**
     * A Model stub that contains a single person.
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

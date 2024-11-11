package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Calendar;
import seedu.address.model.Model;
import seedu.address.model.OperatingHours;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains unit tests for {@code AddCommand}.
 */
public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_appointmentTaken_throwsCommandException() {
        Person validPerson = new PersonBuilder().withAppointment(BOB.getAppointment().dateTime).build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(BOB);

        assertThrows(CommandException.class, AddCommand.MESSAGE_APPOINTMENT_TAKEN, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_appointmentNotWithinOperatingHours_throwsCommandException() {
        Person validPerson = new PersonBuilder().withAppointment(BOB.getAppointment().dateTime).build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(ALICE);
        modelStub.setOperatingHours(LocalTime.of(22, 30), LocalTime.of(23, 30));

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_APPOINMENT_OUTSIDE_OPERATING_HOURS, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_appointmentWithinOperatingHours_addSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().withAppointment(BOB.getAppointment().dateTime).build();
        ModelStub modelStub = new ModelStubWithPerson(ALICE);
        modelStub.setOperatingHours(LocalTime.of(12, 30), LocalTime.of(23, 30));

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
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

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        Person alice = new PersonBuilder().withName("Alice").build();
        AddCommand addCommand = new AddCommand(alice);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + alice + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that has all of the methods failing by default.
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
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Calendar getCalendar() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAppointment(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isValidAppointmentUpdate(Appointment current, Appointment updated) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public OperatingHours getOperatingHours() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean setOperatingHours(LocalTime openingHour, LocalTime closingHour) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean appointmentWithinOperatingHours(Appointment appointment) {
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
        public int backupData(String actionDescription) throws CommandException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path restoreBackup(int index) throws IOException, DataLoadingException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Storage getStorage() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String listAllBackups() throws IOException {
            // Return an empty string or default message
            return "";
        }

        @Override
        public void clearAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;
        private final ArrayList<Person> personsAdded = new ArrayList<>();
        private final List<Appointment> calendar;
        private OperatingHours operatingHours = new OperatingHours();

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
            this.calendar = new ArrayList<>();
            calendar.add(person.getAppointment());
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public boolean hasAppointment(Person person) {
            return calendar.contains(person.getAppointment());
        }

        @Override
        public OperatingHours getOperatingHours() {
            return operatingHours;
        }

        @Override
        public boolean setOperatingHours(LocalTime openingHour, LocalTime closingHour) {
            requireAllNonNull(openingHour, closingHour);
            operatingHours = new OperatingHours(openingHour, closingHour);
            return true;
        }

        @Override
        public boolean appointmentWithinOperatingHours(Appointment appointment) {
            requireNonNull(appointment);
            return operatingHours.isWithinOperatingHours(appointment);
        }
    }

    /**
     * A Model stub that always accepts the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        final AddressBook addressBook = new AddressBook();
        final ArrayList<Appointment> calendar = new ArrayList<>();
        final OperatingHours operatingHours = new OperatingHours();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return addressBook.hasPerson(person);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public boolean hasAppointment(Person person) {
            requireNonNull(person);
            return calendar.stream().anyMatch(x -> x.equals(person.getAppointment()));
        }

        @Override
        public OperatingHours getOperatingHours() {
            return operatingHours;
        }

        @Override
        public boolean setOperatingHours(LocalTime openingHour, LocalTime closingHour) {
            requireAllNonNull(openingHour, closingHour);
            return true;
        }

        @Override
        public boolean appointmentWithinOperatingHours(Appointment appointment) {
            requireNonNull(appointment);
            return operatingHours.isWithinOperatingHours(appointment);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }

    }
}

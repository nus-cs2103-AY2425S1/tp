package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyListings;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AppointmentCommandTest {

    private static final String VALID_DATE = "20/12/2024";
    private static final String VALID_FROM = "0800";
    private static final String VALID_TO = "1000";

    private final Appointment validAppointment = new Appointment(
            new Date(VALID_DATE),
            new From(VALID_FROM),
            new To(VALID_TO)
    );

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        // Test null index
        assertThrows(NullPointerException.class, () -> new AppointmentCommand(null, validAppointment));
    }

    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        // Test null appointment
        assertThrows(NullPointerException.class, () -> new AppointmentCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // Arrange
        ModelStubWithPerson modelStub = new ModelStubWithPerson(new PersonBuilder().buildBuyer());
        Index invalidIndex = Index.fromZeroBased(1);

        AppointmentCommand command = new AppointmentCommand(invalidIndex, validAppointment);

        // Act & Assert
        assertThrows(CommandException.class, () -> command.execute(modelStub),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndex_addAppointmentSuccess() throws Exception {
        // Arrange
        Person personToEdit = new PersonBuilder().withName("Alice").buildBuyer();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personToEdit);

        AppointmentCommand command = new AppointmentCommand(INDEX_FIRST_PERSON, validAppointment);

        // Act
        CommandResult result = command.execute(modelStub);

        // Assert
        Person editedPerson = new PersonBuilder(personToEdit).withAppointment(VALID_DATE, VALID_FROM, VALID_TO)
                .buildBuyer();
        assertEquals(String.format(AppointmentCommand.MESSAGE_ADD_APPOINTMENT_SUCCESS, Messages.format(editedPerson)),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_validIndex_updatesPersonWithAppointment() throws Exception {
        // Arrange
        Person personToEdit = new PersonBuilder().withName("Alice").buildBuyer();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(personToEdit);

        AppointmentCommand command = new AppointmentCommand(INDEX_FIRST_PERSON, validAppointment);

        // Act
        command.execute(modelStub);

        // Assert
        Person editedPerson = new PersonBuilder(personToEdit).withAppointment(VALID_DATE, VALID_FROM, VALID_TO)
                .buildBuyer();
        assertEquals(editedPerson.getAppointment(), validAppointment);
    }

    /**
     * A default model stub that have all of the methods failing.
     * Possible to abstract this
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
        public Path getListingsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setListingsFilePath(Path listingsFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setListings(ReadOnlyListings listings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyListings getListings() {
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
        public Person getPersonByName(Name name) {
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
        public boolean hasListing(Listing listing) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteListing(Listing listing) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addListing(Listing listing) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setListing(Listing target, Listing editedListing) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Listing> getFilteredListingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredListingList(Predicate<Listing> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Listing> getFilteredListingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredListingList(Predicate<Listing> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final List<Person> persons = new ArrayList<>();

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.persons.add(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableList(persons);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            int index = persons.indexOf(target);
            if (index == -1) {
                throw new AssertionError("Target person not found in list.");
            }
            persons.set(index, editedPerson);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            // Stub that doesn't filter persons but is needed to pass the method call
        }
    }

}

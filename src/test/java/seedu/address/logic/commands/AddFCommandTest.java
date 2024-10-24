package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.logging.Filter;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FilteredAppointment.FilteredAppointment;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Appt;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddFCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        AddFCommandTest.ModelStubWithOnlyOnePerson modelStub =
                new AddFCommandTest.ModelStubWithOnlyOnePerson(validPerson);
        Person alice = new PersonBuilder().withNric("T0123456A").build();
        CommandResult commandResult = new AddFCommand(alice).execute(modelStub);

        assertEquals(String.format(AddFCommand.MESSAGE_SUCCESS, Messages.format(alice)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddFCommand addFCommand = new AddFCommand(validPerson);
        AddFCommandTest.ModelStubWithOnlyOnePerson modelStub =
                new AddFCommandTest.ModelStubWithOnlyOnePerson(validPerson);

        assertThrows(CommandException.class, AddFCommand.MESSAGE_DUPLICATE_PERSON, () ->
                addFCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withNric("T0123456A").build();
        Person bob = new PersonBuilder().withNric("S0123456A").build();
        AddFCommand addFAliceCommand = new AddFCommand(alice);
        AddFCommand addFBobCommand = new AddFCommand(bob);

        // same object -> returns true
        assertTrue(addFAliceCommand.equals(addFAliceCommand));

        // same values -> returns true
        AddFCommand addAliceCommandCopy = new AddFCommand(alice);
        assertTrue(addFAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addFAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addFAliceCommand.equals(null));

        // different nric -> returns false
        assertFalse(addFAliceCommand.equals(addFBobCommand));
    }
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
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFilteredAppts(TreeSet<FilteredAppointment> filteredAppointments) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TreeSet<FilteredAppointment> getFilteredAppts() {
            throw new AssertionError("This method should not be called");
        }
    }

    /**
     * A Model stub that will only store 1 person and always accept the person being added
     * unless the person is already stored in the model.
     */
    private class ModelStubWithOnlyOnePerson extends ModelStub {

        private final Person person;

        ModelStubWithOnlyOnePerson(Person person) {
            this.person = person;
        }
        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
        }
    }
}

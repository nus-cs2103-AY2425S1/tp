package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.VendorBuilder;

public class AddVendorCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddVendorCommand(null));
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Vendor validVendor = new VendorBuilder().build();
        AddVendorCommand addVendorCommand = new AddVendorCommand(validVendor);
        AddVendorCommandTest.ModelStub modelStub = new AddVendorCommandTest.ModelStubWithPerson(validVendor);

        assertThrows(CommandException.class,
                AddVendorCommand.MESSAGE_DUPLICATE_PERSON, () -> addVendorCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Amy").build();
        Person bob = new PersonBuilder().withName("Ben").build();
        AddVendorCommand addAmyCommand = new AddVendorCommand(alice);
        AddVendorCommand addBenCommand = new AddVendorCommand(bob);

        // same object -> returns true
        assertTrue(addAmyCommand.equals(addAmyCommand));

        // same values -> returns true
        AddVendorCommand addAliceCommandCopy = new AddVendorCommand(alice);
        assertTrue(addAmyCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAmyCommand.equals(1));

        // null -> returns false
        assertFalse(addAmyCommand.equals(null));

        // different person -> returns false
        assertFalse(addAmyCommand.equals(addBenCommand));
    }

    @Test
    public void toStringMethod() {
        AddVendorCommand addVendorCommand = new AddVendorCommand(ALICE);
        String expected = AddVendorCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addVendorCommand.toString());
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
        public void setTag(Tag target, Tag editedTag) {
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
        public boolean hasTag(Tag toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTag(Tag toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTagList(Predicate<Tag> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getFilteredTagList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasWedding(Wedding toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addWedding(Wedding toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteWedding(Wedding toDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWedding(Wedding target, Wedding editedWedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Wedding> getFilteredWeddingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag toDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonListByTag(Predicate<Tag> tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasVendor(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void assignVendor(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unassignVendor(Person person) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends AddVendorCommandTest.ModelStub {
        private final Vendor vendor;

        ModelStubWithPerson(Vendor vendor) {
            requireNonNull(vendor);
            this.vendor = vendor;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.vendor.isSamePerson(person);
        }

        @Override
        public boolean hasVendor(Person person) {
            requireNonNull(person);
            return this.vendor.isSamePerson(person);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
        }

        @Override
        public void assignVendor(Person person) {

        }

    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends AddVendorCommandTest.ModelStub {
        final ArrayList<Vendor> vendorsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return vendorsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public boolean hasVendor(Person person) {
            requireNonNull(person);
            return vendorsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
        }

        @Override
        public void assignVendor(Person person) {

        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}

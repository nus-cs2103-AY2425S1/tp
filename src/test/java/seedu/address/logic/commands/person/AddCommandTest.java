package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

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
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;

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

        assertEquals(String.format(Messages.MESSAGE_ADD_PERSON_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, Messages.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
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
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
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
        public Person getPerson(Person person) {
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
        public boolean hasTask(Task toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markTask(Task toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unmarkTask(Task toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTask(Task toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(Task toDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTask(Task target, Task editedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonListByTask(Predicate<Task> task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
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
        public Wedding getWedding(Wedding toGet) {
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
        public Tag getTag(Tag toGet) {
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

        @Override
        public void updateFilteredPersonListByWedding(Predicate<Wedding> tag) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

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
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}

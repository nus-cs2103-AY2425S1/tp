package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class RestoreCommandTest {

    @Test
    public void execute_personHasBeenDeleted_restoreSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        ModelStubWithDeletedPerson modelStub = new ModelStubWithDeletedPerson(validPerson);
        CommandResult commandResult = new RestoreCommand().execute(modelStub);

        assertEquals(String.format(RestoreCommand.MESSAGE_RESTORE_PERSON_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(validPerson, modelStub.addedPerson);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        ModelStubWithDeletedPerson modelStub = new ModelStubWithDeletedPerson(validPerson);
        modelStub.addPerson(validPerson);
        RestoreCommand restoreCommand = new RestoreCommand();

        assertThrows(CommandException.class, RestoreCommand.MESSAGE_DUPLICATE_PERSON, () ->
                restoreCommand.execute(modelStub));
    }

    @Test
    public void execute_noDeletedPersonToRestore_throwsCommandException() {
        ModelStubWithoutDeletedPerson modelStub = new ModelStubWithoutDeletedPerson();
        RestoreCommand restoreCommand = new RestoreCommand();
        assertThrows(CommandException.class, RestoreCommand.MESSAGE_NOTHING_TO_RESTORE, () ->
                restoreCommand.execute(modelStub));
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
        public Path getBackupAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBackupAddressBookFilePath(Path backupAddressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void savePersonToDelete(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkRestorable() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void makeNotRestorable() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getLastDeletedPerson() {
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
        public void sortFilteredPersonList(String order, Boolean isSortBySchedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getPersonList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubWithDeletedPerson extends ModelStub {
        private final Person deletedPerson;
        private boolean restorable = true;
        private Person addedPerson;

        ModelStubWithDeletedPerson(Person deletedPerson) {
            requireNonNull(deletedPerson);
            this.deletedPerson = deletedPerson;
        }

        @Override
        public boolean checkRestorable() {
            return restorable;
        }

        @Override
        public void makeNotRestorable() {
            restorable = false;
        }

        @Override
        public Person getLastDeletedPerson() {
            return deletedPerson;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return person.isSamePerson(addedPerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            addedPerson = person;
        }
    }

    private class ModelStubWithoutDeletedPerson extends ModelStub {
        private Person deletedPerson;
        private boolean restorable = false;
        private Person addedPerson;

        ModelStubWithoutDeletedPerson() {
        }

        @Override
        public boolean checkRestorable() {
            return restorable;
        }

        @Override
        public void makeNotRestorable() {
            restorable = false;
        }

        @Override
        public Person getLastDeletedPerson() {
            return deletedPerson;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return person.isSamePerson(addedPerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            addedPerson = person;
        }

    }
}



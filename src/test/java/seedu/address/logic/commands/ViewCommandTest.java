package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.profile.Profile;

public class ViewCommandTest {

    @Test
    public void execute_validTelegramHandle_success() throws CommandException {
        // Test viewing a contact with a valid telegram handle
        ViewCommand command = new ViewCommand(ALICE.getTelegram().value);
        ModelStub modelStub = new ModelStubWithPerson(ALICE);
        CommandResult result = command.execute(modelStub);
        assertEquals(ViewCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(ALICE, result.getViewPerson());
    }

    @Test
    public void execute_nonExistentTelegramHandle_throwsCommandException() {
        // Test trying to view a contact with a non-existent telegram handle
        ViewCommand command = new ViewCommand("nonexistenthandle");
        ModelStub modelStub = new ModelStubWithPerson(ALICE);
        CommandException thrown = Assertions.assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertEquals(String.format(ViewCommand.MESSAGE_NO_SUCH_TELEGRAM, "@nonexistenthandle"), thrown.getMessage());
    }

    @Test
    public void execute_invalidTelegramHandle_throwsCommandException() {
        // Test trying to view a contact with an invalid telegram handle format
        ViewCommand command = new ViewCommand("invalid_handle_format!");
        ModelStub modelStub = new ModelStubWithPerson(ALICE);
        CommandException thrown = Assertions.assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertEquals(String.format(Telegram.MESSAGE_CONSTRAINTS), thrown.getMessage());
    }

    private class ModelStub implements Model {
        @Override
        public void removeFromProfiles(Profile p) {
            throw new AssertionError("This method should not be called");
        }

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
        public HashSet<Profile> getProfiles() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addToProfiles(Profile profileName) {
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
            UniquePersonList u = new UniquePersonList();
            u.add(ALICE);
            return u.asUnmodifiableObservableList();
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            // No-op for the stub
        }

        @Override
        public ObservableList<Person> getSortedPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedPersonListComparator(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSortedListToDefault() {
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
}

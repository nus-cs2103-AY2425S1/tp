package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;

public class BackupCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "BackupCommandTest");

    private static final Path BACKUP_FILE_PRESENT = TEST_DATA_FOLDER.resolve("backup");

    private static final Path BACKUP_ADDRESSBOOK_PRESENT = BACKUP_FILE_PRESENT.resolve("backupAddressBook.json");

    private static final Path MISSING_BACKUP_FILE = TEST_DATA_FOLDER.resolve("backupMissing");

    private static final Path MISSING_BACKUP_ADDRESSBOOK = MISSING_BACKUP_FILE.resolve("backupMissingAddressBook.json");

    private static final Path TYPICAL_ADDRESSBOOK = TEST_DATA_FOLDER.resolve("typicalAddressBook.json");

    private void deleteIfPresent(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            fail();
        }
    }

    private void checkIfIdentical(Path file1, Path file2) {
        try {
            assertEquals(Files.mismatch(file1, file2), -1);
        } catch (IOException e) {
            fail();
        }
    }
    @Test
    public void execute_backupFilePresent_success() {
        Path orignalAddressBook = TYPICAL_ADDRESSBOOK;
        Path backupAddressBook = BACKUP_ADDRESSBOOK_PRESENT;
        Model model = new ModelStub(orignalAddressBook, backupAddressBook);
        String expectedMessage = BackupCommand.MESSAGE_SUCCESS;
        BackupCommand backupCommand = new BackupCommand();

        assertCommandSuccess(backupCommand, model, expectedMessage, model);
        checkIfIdentical(orignalAddressBook, backupAddressBook);;
    }

    @Test
    public void execute_backupFileEmptyAddressBook_success() {
        Path orignalAddressBook = TYPICAL_ADDRESSBOOK;
        Path backupAddressBook = MISSING_BACKUP_ADDRESSBOOK;
        deleteIfPresent(backupAddressBook);
        deleteIfPresent(MISSING_BACKUP_FILE);
        Model model = new ModelStub(orignalAddressBook, backupAddressBook);
        String expectedMessage = BackupCommand.MESSAGE_SUCCESS;
        BackupCommand backupCommand = new BackupCommand();

        assertCommandSuccess(backupCommand, model, expectedMessage, model);
        checkIfIdentical(orignalAddressBook, backupAddressBook);;
    }

    private class ModelStub implements Model {

        private Path addressBookFilePath;

        private Path backupAddressBookFilePath;

        ModelStub(Path addressBookFilePath, Path backupAddressBookFilePath) {
            this.addressBookFilePath = addressBookFilePath;
            this.backupAddressBookFilePath = backupAddressBookFilePath;
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
            return addressBookFilePath;
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getBackupAddressBookFilePath() {
            return backupAddressBookFilePath;
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
}

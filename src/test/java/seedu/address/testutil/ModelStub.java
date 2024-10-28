package seedu.address.testutil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.AddCommandTest;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

import static java.util.Objects.requireNonNull;

public class ModelStub implements Model {
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
    public boolean hasPhone(Person person) {
        requireNonNull(person);
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasEmail(Person person) {
        requireNonNull(person);
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
    public void addWedding(Wedding wedding) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasWedding(Wedding wedding) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteWedding(Wedding target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setWedding(Wedding target, Wedding editedWedding) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Wedding> getFilteredWeddingList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
        throw new AssertionError("This method should not be called.");
    }
}

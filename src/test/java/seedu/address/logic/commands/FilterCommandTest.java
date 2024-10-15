package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;


public class FilterCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        FilterCommand filterFirstCommand = new FilterCommand(new Tag("friends"));
        FilterCommand filterSecondCommand = new FilterCommand(new Tag("colleagues"));

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(new Tag("friends"));
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_noTagFilteredFor_showsNothing() {
        expectedModel.updateFilteredPersonList(person -> false);
        String[] absentTags = {
            "bestFriends",
            "Colleagues",
            "4Horsemen"
        };
        for (String absentTag : absentTags) {
            FilterCommand filterCommand = new FilterCommand(new Tag(absentTag));
            assertCommandSuccess(
                    filterCommand,
                    model,
                    String.format(FilterCommand.MESSAGE_SUCCESS, absentTag),
                    expectedModel
            );
        }
    }

    @Test
    public void execute_tagFilteredForPresent_showsFiltered() {
        String[] presentTags = {
            "owesMoney",
            "friends"
        };
        for (String presentTag : presentTags) {
            FilterCommand filterCommand = new FilterCommand(new Tag(presentTag));
            expectedModel.updateFilteredPersonList(
                    person -> person.getTags().contains(new Tag(presentTag))
            );
            assertCommandSuccess(
                    filterCommand,
                    model,
                    String.format(FilterCommand.MESSAGE_SUCCESS, presentTag),
                    expectedModel
            );
        }
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
        public void sortFilteredPersonList(String order) {
            throw new AssertionError("This method should not be called.");
        }
    }

}

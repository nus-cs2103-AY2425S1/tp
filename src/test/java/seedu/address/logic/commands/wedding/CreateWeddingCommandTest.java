package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWeddings.VALID_WEDDING_NAME_AMY_WEDDING;
import static seedu.address.testutil.TypicalWeddings.VALID_WEDDING_NAME_BOB_WEDDING;

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

public class CreateWeddingCommandTest {

    @Test
    public void constructor_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateWeddingCommand(null));
    }

    @Test
    public void execute_weddingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingWeddingAdded modelStub = new ModelStubAcceptingWeddingAdded();
        Wedding validWedding = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);

        CommandResult commandResult = new CreateWeddingCommand(validWedding).execute(modelStub);
        assertEquals(String.format(CreateWeddingCommand.MESSAGE_SUCCESS, Messages.format(validWedding)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validWedding), modelStub.weddingsAdded);
    }

    @Test
    public void execute_duplicateWedding_throwsCommandException() {
        Wedding validWedding = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);
        CreateWeddingCommand createWeddingCommand = new CreateWeddingCommand(validWedding);
        ModelStub modelStub = new ModelStubWithWedding(validWedding);

        assertThrows(CommandException.class,
                CreateWeddingCommand.MESSAGE_DUPLICATE_WEDDING, () -> createWeddingCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Wedding amyWedding = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);
        Wedding bobWedding = new Wedding(VALID_WEDDING_NAME_BOB_WEDDING);
        CreateWeddingCommand createAmyWeddingCommand = new CreateWeddingCommand(amyWedding);
        CreateWeddingCommand createBobWeddingCommand = new CreateWeddingCommand(bobWedding);

        // same object -> returns ture
        assertEquals(createAmyWeddingCommand, createAmyWeddingCommand);

        // same values -> returns false
        CreateWeddingCommand createFloristCommandCopy = new CreateWeddingCommand(amyWedding);
        assertEquals(createAmyWeddingCommand, createFloristCommandCopy);

        // different types -> returns false
        assertFalse(createAmyWeddingCommand.equals(1));

        // null -> return false
        assertFalse(createAmyWeddingCommand.equals(null));

        // different Wedding -> return false
        assertFalse(createAmyWeddingCommand.equals(createBobWeddingCommand));
    }

    /**
     * A default model stub that have all methods failing.
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
        public void setTag(Tag target, Tag editedWedding) {
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
        public void updateFilteredPersonListByTag(Predicate<Tag> tagPredicate) {
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
        public boolean hasTask(Task toAdd) {
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
        public void setWedding(Wedding target, Wedding editedWedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ObservableList<Tag> getFilteredTagList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonListByWedding(Predicate<Wedding> tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Wedding> getFilteredWeddingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteWedding(Wedding wedding) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single Wedding.
     */
    private class ModelStubWithWedding extends CreateWeddingCommandTest.ModelStub {
        private final Wedding wedding;

        ModelStubWithWedding(Wedding wedding) {
            requireNonNull(wedding);
            this.wedding = wedding;
        }

        @Override
        public boolean hasWedding(Wedding wedding) {
            requireNonNull(wedding);
            return this.wedding.isSameWedding(wedding);
        }
    }

    /**
     * A Model stub that always accept the Wedding being added.
     */
    private class ModelStubAcceptingWeddingAdded extends CreateWeddingCommandTest.ModelStub {
        final ArrayList<Wedding> weddingsAdded = new ArrayList<>();

        @Override
        public boolean hasWedding(Wedding wedding) {
            requireNonNull(wedding);
            return weddingsAdded.stream().anyMatch(wedding::isSameWedding);
        }

        @Override
        public void addWedding(Wedding wedding) {
            requireNonNull(wedding);
            weddingsAdded.add(wedding);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}

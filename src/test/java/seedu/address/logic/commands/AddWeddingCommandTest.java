package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyWeddingBook;
import seedu.address.model.WeddingBook;
import seedu.address.model.person.JobContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingNameContainsKeywordsPredicate;
import seedu.address.testutil.WeddingBuilder;

public class AddWeddingCommandTest {

    @Test
    public void constructor_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddWeddingCommand(null));
    }

    @Test
    public void execute_weddingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingWeddingAdded modelStub = new ModelStubAcceptingWeddingAdded();
        Wedding validWedding = new WeddingBuilder().build();

        CommandResult commandResult = new AddWeddingCommand(validWedding).execute(modelStub);

        assertEquals(String.format(AddWeddingCommand.MESSAGE_SUCCESS, Messages.format(validWedding)),
                commandResult.getFeedbackToUser());
        assertEquals(List.of(validWedding), modelStub.weddingsAdded);
    }

    @Test
    public void execute_duplicateWedding_throwsCommandException() {
        Wedding validWedding = new WeddingBuilder().build();
        AddWeddingCommand addWeddingCommand = new AddWeddingCommand(validWedding);
        ModelStub modelStub = new ModelStubWithWedding(validWedding);

        assertThrows(CommandException.class,
                AddWeddingCommand.MESSAGE_DUPLICATE_WEDDING, () -> addWeddingCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Wedding weddingOne = new WeddingBuilder().withWeddingName("John Loh & Jean Tan").build();
        Wedding weddingTwo = new WeddingBuilder().withWeddingName("Alice Tan & Bob Lee").build();
        AddWeddingCommand addWeddingOneCommand = new AddWeddingCommand(weddingOne);
        AddWeddingCommand addWeddingTwoCommand = new AddWeddingCommand(weddingTwo);

        // same object -> returns true
        assertTrue(addWeddingOneCommand.equals(addWeddingOneCommand));

        // same values -> returns true
        AddWeddingCommand addWeddingOneCommandCopy = new AddWeddingCommand(weddingOne);
        assertTrue(addWeddingOneCommand.equals(addWeddingOneCommandCopy));

        // different types -> returns false
        assertFalse(addWeddingOneCommand.equals(1));

        // null -> returns false
        assertFalse(addWeddingOneCommand.equals(null));

        // different wedding -> returns false
        assertFalse(addWeddingOneCommand.equals(addWeddingTwoCommand));
    }

    @Test
    public void toStringMethod() {
        AddWeddingCommand addWeddingCommand = new AddWeddingCommand(WEDDING_ONE);
        String expected = AddWeddingCommand.class.getCanonicalName() + "{toAdd=" + WEDDING_ONE + "}";
        assertEquals(expected, addWeddingCommand.toString());
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
        public Path getWeddingBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWeddingBookFilePath(Path addressBookFilePath) {
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(JobContainsKeywordsPredicate predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addWedding(Wedding wedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWeddingBook(ReadOnlyWeddingBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyWeddingBook getWeddingBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasWedding(Wedding wedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteWedding(Wedding wedding) {
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

        @Override
        public void updateFilteredWeddingList(WeddingNameContainsKeywordsPredicate predicate) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single wedding.
     */
    private class ModelStubWithWedding extends ModelStub {
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
     * A Model stub that always accept the wedding being added.
     */
    private class ModelStubAcceptingWeddingAdded extends ModelStub {
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
        public ReadOnlyWeddingBook getWeddingBook() {
            return new WeddingBook();
        }
    }

}


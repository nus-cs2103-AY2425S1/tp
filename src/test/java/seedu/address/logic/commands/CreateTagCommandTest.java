package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FLORIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PHOTOGRAPHER;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;
import seedu.address.model.wedding.Wedding;

public class CreateTagCommandTest {

    @Test
    public void constructor_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateTagCommand(null));
    }

    @Test
    public void execute_tagAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTagAdded modelStub = new ModelStubAcceptingTagAdded();
        Tag validTag = new Tag(new TagName(VALID_TAG_FLORIST));

        CommandResult commandResult = new CreateTagCommand(validTag).execute(modelStub);
        assertEquals(String.format(CreateTagCommand.MESSAGE_SUCCESS, Messages.format(validTag)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTag), modelStub.tagsAdded);
    }

    @Test
    public void execute_duplicateTag_throwsCommandException() {
        Tag validTag = new Tag(new TagName(VALID_TAG_FLORIST));
        CreateTagCommand createTagCommand = new CreateTagCommand(validTag);
        ModelStub modelStub = new ModelStubWithTag(validTag);

        assertThrows(CommandException.class,
                CreateTagCommand.MESSAGE_DUPLICATE_TAG, () -> createTagCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Tag florist = new Tag(new TagName(VALID_TAG_FLORIST));
        Tag photographer = new Tag(new TagName(VALID_TAG_PHOTOGRAPHER));
        CreateTagCommand createFloristCommand = new CreateTagCommand(florist);
        CreateTagCommand createPhotographerCommand = new CreateTagCommand(photographer);

        // same object -> returns ture
        assertEquals(createFloristCommand, createFloristCommand);

        // same values -> returns false
        CreateTagCommand createFloristCommandCopy = new CreateTagCommand(florist);
        assertEquals(createFloristCommand, createFloristCommandCopy);

        // different types -> returns false
        assertFalse(createFloristCommand.equals(1));

        // null -> return false
        assertFalse(createFloristCommand.equals(null));

        // different tag -> return false
        assertFalse(createFloristCommand.equals(createPhotographerCommand));
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
        public void deleteWedding(Wedding tag) {
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
        public void updateFilteredPersonListByWedding(Predicate<Wedding> tag) {
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
        public void deleteTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single tag.
     */
    private class ModelStubWithTag extends CreateTagCommandTest.ModelStub {
        private final Tag tag;

        ModelStubWithTag(Tag tag) {
            requireNonNull(tag);
            this.tag = tag;
        }

        @Override
        public boolean hasTag(Tag tag) {
            requireNonNull(tag);
            return this.tag.isSameTag(tag);
        }
    }

    /**
     * A Model stub that always accept the tag being added.
     */
    private class ModelStubAcceptingTagAdded extends CreateTagCommandTest.ModelStub {
        final ArrayList<Tag> tagsAdded = new ArrayList<>();

        @Override
        public boolean hasTag(Tag tag) {
            requireNonNull(tag);
            return tagsAdded.stream().anyMatch(tag::isSameTag);
        }

        @Override
        public void addTag(Tag tag) {
            requireNonNull(tag);
            tagsAdded.add(tag);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}

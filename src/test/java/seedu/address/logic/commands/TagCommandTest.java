package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.BRIDES_SIDE;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for TagCommand.
 */
public class TagCommandTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addTag(BRIDES_SIDE);
        model.addTag(new Tag("anotherTag"));
    }

    /**
     * EP: Valid tag successfully tagged on person (i.e. Tag is in tag list but not on person).
     */
    @Test
    public void execute_tagExistsAndNotOnPerson_success() {
        Tag validTag = new Tag("uniqueTag");
        model.addTag(validTag);
        Person personToTag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertFalse(personToTag.getTags().contains(validTag));

        Set<Tag> tags = new HashSet<>();
        List<Index> indexes = new ArrayList<>();
        tags.add(validTag);
        indexes.add(INDEX_FIRST_PERSON);
        TagCommand tagCommand = new TagCommand(indexes, tags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> updatedTags = new HashSet<>(personToTag.getTags());
        updatedTags.add(validTag);

        Person updatedPerson = new Person(personToTag.getName(), personToTag.getPhone(), personToTag.getEmail(),
                                          RsvpStatus.PENDING, updatedTags);
        expectedModel.setPerson(personToTag, updatedPerson);
        expectedModel.addTag(validTag);
        String expectedMessage = TagCommand.MESSAGE_TAG_PERSON_SUCCESS + Messages.format(updatedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Valid tag successfully tagged on multiple people (i.e. Tag is in tag list but not on multiple people).
     */
    @Test
    public void execute_tagExistsAndNotOnMultiplePersons_success() {
        Tag validTag = new Tag("uniqueTag");
        model.addTag(validTag);
        Person firstPersonToTag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPersonToTag = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        assertFalse(firstPersonToTag.getTags().contains(validTag));
        assertFalse(secondPersonToTag.getTags().contains(validTag));

        Set<Tag> tags = new HashSet<>();
        List<Index> indexes = new ArrayList<>();
        tags.add(validTag);
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        TagCommand tagCommand = new TagCommand(indexes, tags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> firstPersonUpdatedTags = new HashSet<>(firstPersonToTag.getTags());
        firstPersonUpdatedTags.add(validTag);
        Set<Tag> secondPersonUpdatedTags = new HashSet<>(secondPersonToTag.getTags());
        secondPersonUpdatedTags.add(validTag);

        Person firstUpdatedPerson = new Person(firstPersonToTag.getName(), firstPersonToTag.getPhone(),
                firstPersonToTag.getEmail(), RsvpStatus.PENDING, firstPersonUpdatedTags);
        expectedModel.setPerson(firstPersonToTag, firstUpdatedPerson);

        Person secondUpdatedPerson = new Person(secondPersonToTag.getName(), secondPersonToTag.getPhone(),
                secondPersonToTag.getEmail(), RsvpStatus.PENDING, secondPersonUpdatedTags);
        expectedModel.setPerson(secondPersonToTag, secondUpdatedPerson);
        expectedModel.addTag(validTag);
        String expectedMessage = TagCommand.MESSAGE_TAG_PERSON_SUCCESS
                + Messages.format(firstUpdatedPerson) + "\n"
                + Messages.format(secondUpdatedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Valid tag successfully tagged on some people but not on others (i.e. Tag is in tag list, is not already on
     * some but on others).
     */
    @Test
    public void execute_bulkTagging_someGuestsAlreadyHaveTagReportsProperly() {
        // Guests at indexes 1 and 2 has the tag "friends", but guest at index 3 does not
        Tag friendTag = FRIENDS;
        model.addTag(friendTag);
        assertTrue(model.hasTag(friendTag));

        List<Index> indexes = List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        Set<Tag> tags = new HashSet<>();
        tags.add(friendTag);
        TagCommand tagCommand = new TagCommand(indexes, tags);

        Person personToTag = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> updatedTags = new HashSet<>(personToTag.getTags());
        updatedTags.add(friendTag);

        Person updatedPerson = new Person(personToTag.getName(), personToTag.getPhone(), personToTag.getEmail(),
                RsvpStatus.PENDING, updatedTags);
        expectedModel.setPerson(personToTag, updatedPerson);
        expectedModel.addTag(friendTag);
        String expectedSuccessMessage = TagCommand.MESSAGE_TAG_PERSON_SUCCESS + Messages.format(updatedPerson);
        String expectedDuplicateMessage = TagCommand.MESSAGE_DUPLICATE_TAG + friendTag;

        String expectedMessage = expectedSuccessMessage + "\n" + expectedDuplicateMessage;

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Some tags are invalid (i.e. Some tags already in tag list, some tags are not).
     */
    @Test
    public void execute_bulkTagging_someTagsNotCreatedReportsProperly() {
        // Tags "bride's side" and "friends" are created, but not "nonexistent" tag
        Tag friendTag = FRIENDS;
        Tag nonExistentTag = new Tag("nonexistent");
        model.addTag(friendTag);
        assertTrue(model.hasTag(BRIDES_SIDE));
        assertTrue(model.hasTag(friendTag));
        assertFalse(model.hasTag(nonExistentTag));

        List<Index> indexes = List.of(INDEX_THIRD_PERSON);
        Set<Tag> tags = new HashSet<>();
        tags.add(friendTag);
        tags.add(BRIDES_SIDE);
        tags.add(nonExistentTag);
        TagCommand tagCommand = new TagCommand(indexes, tags);

        Person personToTag = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> updatedTags = new HashSet<>(personToTag.getTags());
        updatedTags.add(friendTag);
        updatedTags.add(BRIDES_SIDE);

        Person updatedPerson = new Person(personToTag.getName(), personToTag.getPhone(), personToTag.getEmail(),
                RsvpStatus.PENDING, updatedTags);
        expectedModel.setPerson(personToTag, updatedPerson);
        expectedModel.addTag(friendTag);
        expectedModel.addTag(BRIDES_SIDE);
        String expectedSuccessMessage = TagCommand.MESSAGE_TAG_PERSON_SUCCESS + Messages.format(updatedPerson);
        String expectedTagNotCreatedMessage = TagCommand.MESSAGE_TAG_NOT_CREATED + nonExistentTag;

        String expectedMessage = expectedSuccessMessage + "\n" + expectedTagNotCreatedMessage;

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Index out of bounds (i.e. Index greater than list size).
     */
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> indexList = new ArrayList<>();
        indexList.add(outOfBoundIndex);
        Set<Tag> tags = new HashSet<>();
        tags.add(BRIDES_SIDE);
        TagCommand tagCommand = new TagCommand(indexList, tags);

        assertCommandFailure(tagCommand, model, TagCommand.MESSAGE_INVALID_INDEX
                + model.getFilteredPersonList().size() + ")");
    }

    /**
     * EP: Undo a successful tag command (i.e. Previous successful tag command is undone).
     */
    @Test
    public void execute_undoTagCommand_success() {
        Model originalModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Tag validTag = new Tag("uniqueTag");
        model.addTag(validTag);
        Person personToTag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertFalse(personToTag.getTags().contains(validTag));

        Set<Tag> tags = new HashSet<>();
        List<Index> indexes = new ArrayList<>();
        tags.add(validTag);
        indexes.add(INDEX_FIRST_PERSON);
        TagCommand tagCommand = new TagCommand(indexes, tags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> updatedTags = new HashSet<>(personToTag.getTags());
        updatedTags.add(validTag);

        Person updatedPerson = new Person(personToTag.getName(), personToTag.getPhone(), personToTag.getEmail(),
                RsvpStatus.PENDING, updatedTags);
        expectedModel.setPerson(personToTag, updatedPerson);
        expectedModel.addTag(validTag);
        String expectedMessage = TagCommand.MESSAGE_TAG_PERSON_SUCCESS + Messages.format(updatedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);

        model.updatePreviousCommand(tagCommand);
        UndoCommand undoCommand = new UndoCommand();
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, originalModel);
    }

    /**
     * EP: Attempting to undo an unsuccessful tag command involving invalid tag.
     */
    @Test
    public void execute_undoTagCommandWithTagNotCreated_failure() {
        Model originalModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Tag nonExistentTag = new Tag("nonExistentTag");
        assertFalse(model.hasTag(nonExistentTag));

        Set<Tag> tags = new HashSet<>();
        List<Index> indexes = new ArrayList<>();
        tags.add(nonExistentTag);
        indexes.add(INDEX_FIRST_PERSON);
        TagCommand tagCommand = new TagCommand(indexes, tags);

        String expectedFailureMessage = TagCommand.MESSAGE_TAG_NOT_CREATED + nonExistentTag;
        assertCommandSuccess(tagCommand, model, expectedFailureMessage, originalModel);

        UndoCommand undoCommand = new UndoCommand();
        String expectedUndoMessage = UndoCommand.MESSAGE_NO_PREVIOUS_COMMAND;
        assertCommandFailure(undoCommand, model, expectedUndoMessage);
    }

    /**
     * EP: Attempting to undo an unsuccessful tag command involving invalid index.
     */
    @Test
    public void execute_undoTagCommandWithInvalidIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> indexList = new ArrayList<>();
        indexList.add(outOfBoundIndex);
        Set<Tag> tags = new HashSet<>();
        tags.add(BRIDES_SIDE);
        TagCommand tagCommand = new TagCommand(indexList, tags);

        assertCommandFailure(tagCommand, model, TagCommand.MESSAGE_INVALID_INDEX
                + model.getFilteredPersonList().size() + ")");

        UndoCommand undoCommand = new UndoCommand();
        String expectedUndoMessage = UndoCommand.MESSAGE_NO_PREVIOUS_COMMAND;
        assertCommandFailure(undoCommand, model, expectedUndoMessage);
    }

    /**
     * EP: Undo a partially successful tag command involving some invalid tags.
     */
    @Test
    public void execute_undoTagCommandWithMixedValidityTags_successfullyUndone() {
        Tag nonExistentTag = new Tag("nonexistent");

        model.addTag(BRIDES_SIDE);
        assertFalse(model.hasTag(nonExistentTag));

        List<Index> indexes = List.of(INDEX_FIRST_PERSON);
        Set<Tag> tags = new HashSet<>();
        tags.add(BRIDES_SIDE);
        tags.add(nonExistentTag);
        TagCommand tagCommand = new TagCommand(indexes, tags);

        Person personToTag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> updatedTags = new HashSet<>(personToTag.getTags());
        updatedTags.add(BRIDES_SIDE);
        Person updatedPerson = new Person(personToTag.getName(), personToTag.getPhone(), personToTag.getEmail(),
                personToTag.getRsvpStatus(), updatedTags);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToTag, updatedPerson);

        String expectedMessage = TagCommand.MESSAGE_TAG_PERSON_SUCCESS + Messages.format(updatedPerson)
                + "\n" + TagCommand.MESSAGE_TAG_NOT_CREATED + nonExistentTag;
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);

        model.updatePreviousCommand(tagCommand);
        UndoCommand undoCommand = new UndoCommand();
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void equals() {
        Tag anotherTag = new Tag("anotherTag");

        Set<Tag> bridesSideTagSet = new HashSet<>();
        bridesSideTagSet.add(BRIDES_SIDE);

        Set<Tag> anotherTagSet = new HashSet<>();
        anotherTagSet.add(anotherTag);

        List<Index> firstIndex = new ArrayList<>();
        firstIndex.add(INDEX_FIRST_PERSON);

        List<Index> secondIndex = new ArrayList<>();
        secondIndex.add(INDEX_SECOND_PERSON);

        TagCommand tagFirstCommand = new TagCommand(firstIndex, bridesSideTagSet);
        TagCommand tagSecondCommand = new TagCommand(secondIndex, bridesSideTagSet);
        TagCommand tagFirstDifferentTagCommand = new TagCommand(firstIndex, anotherTagSet);

        // same object -> returns true
        assertTrue(tagFirstCommand.equals(tagFirstCommand));

        // same values -> returns true
        TagCommand tagFirstCommandCopy = new TagCommand(firstIndex, bridesSideTagSet);
        assertTrue(tagFirstCommand.equals(tagFirstCommandCopy));

        // different types -> returns false
        assertFalse(tagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(tagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(tagFirstCommand.equals(tagSecondCommand));

        // different tag -> returns false
        assertFalse(tagFirstCommand.equals(tagFirstDifferentTagCommand));
    }
}

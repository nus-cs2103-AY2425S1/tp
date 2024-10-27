package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.BRIDES_SIDE;

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

    @Test
    public void execute_bulkTagging_someGuestsAlreadyHaveTagReportsProperly() {
        // Guests at indexes 1 and 2 has the tag "friends", but guest at index 3 does not
        Tag friendTag = new Tag("friends");
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

    @Test
    public void execute_bulkTagging_someTagsNotCreatedReportsProperly() {
        // Tags "bride's side" and "friends" are created, but not "nonexistent" tag
        Tag friendTag = new Tag("friends");
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

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> indexList = new ArrayList<>();
        indexList.add(outOfBoundIndex);
        Set<Tag> tags = new HashSet<>();
        tags.add(BRIDES_SIDE);
        TagCommand tagCommand = new TagCommand(indexList, tags);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

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
import static seedu.address.testutil.TypicalTags.FRIENDS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UntagCommand.
 */
public class UntagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexAndTag_success() {
        Tag tag = new Tag("friends");
        Person personToUntag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertTrue(personToUntag.getTags().contains(tag));

        Set<Tag> tags = new HashSet<>();
        List<Index> indexes = new ArrayList<>();
        tags.add(tag);
        indexes.add(INDEX_FIRST_PERSON);
        UntagCommand untagCommand = new UntagCommand(indexes, tags);

        Set<Tag> updatedTags = new HashSet<>(personToUntag.getTags());
        updatedTags.remove(tag);

        Person modifiedPerson = new Person(
                personToUntag.getName(),
                personToUntag.getPhone(),
                personToUntag.getEmail(),
                personToUntag.getRsvpStatus(),
                updatedTags
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToUntag, modifiedPerson);

        String expectedMessage = UntagCommand.MESSAGE_UNTAG_PERSON_SUCCESS + Messages.format(modifiedPerson);

        assertCommandSuccess(untagCommand, model, expectedMessage, expectedModel);
        assertFalse(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getTags().contains(tag));
    }

    @Test
    public void execute_tagExistsAndOnMultiplePersons_success() {
        Tag tag = new Tag("friends");
        Person firstPersonToTag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPersonToTag = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        assertTrue(firstPersonToTag.getTags().contains(tag));
        assertTrue(secondPersonToTag.getTags().contains(tag));

        Set<Tag> tags = new HashSet<>();
        List<Index> indexes = new ArrayList<>();
        tags.add(tag);
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        UntagCommand untagCommand = new UntagCommand(indexes, tags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> firstPersonUpdatedTags = new HashSet<>(firstPersonToTag.getTags());
        firstPersonUpdatedTags.remove(tag);
        Set<Tag> secondPersonUpdatedTags = new HashSet<>(secondPersonToTag.getTags());
        secondPersonUpdatedTags.remove(tag);

        Person firstUpdatedPerson = new Person(firstPersonToTag.getName(), firstPersonToTag.getPhone(),
                firstPersonToTag.getEmail(), RsvpStatus.PENDING, firstPersonUpdatedTags);
        expectedModel.setPerson(firstPersonToTag, firstUpdatedPerson);

        Person secondUpdatedPerson = new Person(secondPersonToTag.getName(), secondPersonToTag.getPhone(),
                secondPersonToTag.getEmail(), RsvpStatus.PENDING, secondPersonUpdatedTags);
        expectedModel.setPerson(secondPersonToTag, secondUpdatedPerson);
        String expectedMessage = UntagCommand.MESSAGE_UNTAG_PERSON_SUCCESS
                + Messages.format(firstUpdatedPerson) + "\n"
                + Messages.format(secondUpdatedPerson);

        assertCommandSuccess(untagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> indexList = new ArrayList<>();
        indexList.add(outOfBoundIndex);
        Set<Tag> tags = new HashSet<>();
        tags.add(BRIDES_SIDE);
        UntagCommand untagCommand = new UntagCommand(indexList, tags);

        assertCommandFailure(untagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_bulkUntagging_someTagsNotFoundReportsProperly() {
        // Tags "friends" are on guests at indexes 1 and 2, but 3
        Tag friendTag = FRIENDS;

        List<Index> indexes = List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        Set<Tag> tags = new HashSet<>();
        tags.add(friendTag);
        UntagCommand untagCommand = new UntagCommand(indexes, tags);

        Person firstPersonToUntag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPersonToUntag = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> firstPersonUpdatedTags = new HashSet<>(firstPersonToUntag.getTags());
        firstPersonUpdatedTags.remove(friendTag);
        Set<Tag> secondPersonUpdatedTags = new HashSet<>(secondPersonToUntag.getTags());
        secondPersonUpdatedTags.remove(friendTag);

        Person firstUpdatedPerson = new Person(firstPersonToUntag.getName(), firstPersonToUntag.getPhone(),
                firstPersonToUntag.getEmail(), RsvpStatus.PENDING, firstPersonUpdatedTags);
        expectedModel.setPerson(firstPersonToUntag, firstUpdatedPerson);
        Person secondUpdatedPerson = new Person(secondPersonToUntag.getName(), secondPersonToUntag.getPhone(),
                secondPersonToUntag.getEmail(), RsvpStatus.PENDING, secondPersonUpdatedTags);
        expectedModel.setPerson(secondPersonToUntag, secondUpdatedPerson);
        String expectedSuccessMessage = UntagCommand.MESSAGE_UNTAG_PERSON_SUCCESS + Messages.format(firstUpdatedPerson)
                + "\n" + Messages.format(secondUpdatedPerson);
        String expectedTagNotFoundMessage = UntagCommand.MESSAGE_TAG_NOT_FOUND + friendTag;

        String expectedMessage = expectedSuccessMessage + "\n" + expectedTagNotFoundMessage;

        assertCommandSuccess(untagCommand, model, expectedMessage, expectedModel);
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

        UntagCommand untagFirstCommand = new UntagCommand(firstIndex, bridesSideTagSet);
        UntagCommand untagSecondCommand = new UntagCommand(secondIndex, bridesSideTagSet);
        UntagCommand untagFirstDifferentUntagCommand = new UntagCommand(firstIndex, anotherTagSet);

        // same object -> returns true
        assertTrue(untagFirstCommand.equals(untagFirstCommand));

        // same values -> returns true
        UntagCommand untagFirstCommandCopy = new UntagCommand(firstIndex, bridesSideTagSet);
        assertTrue(untagFirstCommand.equals(untagFirstCommandCopy));

        // different types -> returns false
        assertFalse(untagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(untagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(untagFirstCommand.equals(untagSecondCommand));

        // different tag -> returns false
        assertFalse(untagFirstCommand.equals(untagFirstDifferentUntagCommand));
    }
}

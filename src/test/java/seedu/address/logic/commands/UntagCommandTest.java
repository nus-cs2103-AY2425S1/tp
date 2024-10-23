package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.BRIDES_SIDE;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
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
        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, tag);

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

        String expectedMessage = String.format(UntagCommand.MESSAGE_UNTAG_PERSON_SUCCESS,
                Messages.format(modifiedPerson));

        assertCommandSuccess(untagCommand, model, expectedMessage, expectedModel);
        assertFalse(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getTags().contains(BRIDES_SIDE));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UntagCommand untagCommand = new UntagCommand(outOfBoundIndex, BRIDES_SIDE);

        assertCommandFailure(untagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_tagNotFound_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToUntag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Set<Tag> initialTags = new HashSet<>(personToUntag.getTags());
        initialTags.remove(new Tag("nonexistenttag"));

        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, new Tag("nonexistenttag"));
        assertCommandFailure(untagCommand, model, UntagCommand.MESSAGE_TAG_NOT_FOUND);
    }

    @Test
    public void equals() {
        Tag anotherTag = new Tag("anotherTag");
        UntagCommand untagFirstCommand = new UntagCommand(INDEX_FIRST_PERSON, BRIDES_SIDE);
        UntagCommand untagSecondCommand = new UntagCommand(INDEX_SECOND_PERSON, BRIDES_SIDE);
        UntagCommand untagFirstDifferentTagCommand = new UntagCommand(INDEX_FIRST_PERSON, anotherTag);

        // same object -> returns true
        assertTrue(untagFirstCommand.equals(untagFirstCommand));

        // same values -> returns true
        UntagCommand untagFirstCommandCopy = new UntagCommand(INDEX_FIRST_PERSON, BRIDES_SIDE);
        assertTrue(untagFirstCommand.equals(untagFirstCommandCopy));

        // different types -> returns false
        assertFalse(untagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(untagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(untagFirstCommand.equals(untagSecondCommand));

        // different tag -> returns false
        assertFalse(untagFirstCommand.equals(untagFirstDifferentTagCommand));
    }
}

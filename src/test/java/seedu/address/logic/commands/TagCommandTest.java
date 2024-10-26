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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        tags.add(validTag);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, tags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> updatedTags = new HashSet<>(personToTag.getTags());
        updatedTags.add(validTag);

        Person updatedPerson = new Person(personToTag.getName(), personToTag.getPhone(), personToTag.getEmail(),
                                          RsvpStatus.PENDING, updatedTags);
        expectedModel.setPerson(personToTag, updatedPerson);
        expectedModel.addTag(validTag);
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedPerson));

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagAlreadyOnPerson_throwsCommandException() {
        Tag existingTag = new Tag("friends");
        model.addTag(existingTag);
        Person originalPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Set<Tag> newTags = new HashSet<>(originalPerson.getTags());
        newTags.add(existingTag);
        Person personToTag = new Person(originalPerson.getName(), originalPerson.getPhone(), originalPerson.getEmail(),
                                        RsvpStatus.PENDING, newTags);

        model.setPerson(originalPerson, personToTag);

        Set<Tag> tags = new HashSet<>();
        tags.add(existingTag);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, tags);

        assertCommandFailure(tagCommand, model, TagCommand.MESSAGE_DUPLICATE_TAG + existingTag);
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
    public void execute_tagNotCreatedYet_throwsCommandException() {
        Tag nonExistentTag = new Tag("nonexistent");
        assertFalse(model.hasTag(nonExistentTag));

        Set<Tag> tags = new HashSet<>();
        tags.add(nonExistentTag);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, tags);

        assertCommandFailure(tagCommand, model,
                TagCommand.MESSAGE_TAG_NOT_CREATED + nonExistentTag);
    }

    @Test
    public void equals() {
        Tag anotherTag = new Tag("anotherTag");

        Set<Tag> bridesSideTagSet = new HashSet<>();
        bridesSideTagSet.add(BRIDES_SIDE);

        Set<Tag> anotherTagSet = new HashSet<>();
        anotherTagSet.add(anotherTag);

        TagCommand tagFirstCommand = new TagCommand(INDEX_FIRST_PERSON, bridesSideTagSet);
        TagCommand tagSecondCommand = new TagCommand(INDEX_SECOND_PERSON, bridesSideTagSet);
        TagCommand tagFirstDifferentTagCommand = new TagCommand(INDEX_FIRST_PERSON, anotherTagSet);

        // same object -> returns true
        assertTrue(tagFirstCommand.equals(tagFirstCommand));

        // same values -> returns true
        TagCommand tagFirstCommandCopy = new TagCommand(INDEX_FIRST_PERSON, bridesSideTagSet);
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

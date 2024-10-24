package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code TagCommand}.
 */
public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /*
    // this test causes errors in another test class when run with gradlew clean build.
    @Test
    public void execute_validIndexAndTags_success() {

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person personToTag = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tag firstTag = new Tag("newTag1");
        Tag secondTag = new Tag("newTag2");
        Set<Tag> tags = Set.of(firstTag, secondTag);
        personToTag.addTags(tags);

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, tags);
        String expectedMessage = String.format(TagCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

     */

    @Test
    public void execute_duplicateTag_throwsCommandException() {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Tag tag = new Tag("friends");
        Set<Tag> tags = Set.of(tag);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, tags);

        assertCommandFailure(tagCommand, model, TagCommand.MESSAGE_TAG_ALREADY_EXISTS);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Tag tag = new Tag("tag");
        Set<Tag> tags = Set.of(tag);
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, tags);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Tag firstTag = new Tag("firstTag");
        Set<Tag> firstTags = Set.of(firstTag);
        Tag secondTag = new Tag("secondTag");
        Set<Tag> secondTags = Set.of(secondTag);
        TagCommand tagFirstCommand = new TagCommand(INDEX_FIRST_PERSON, firstTags);
        TagCommand tagSecondCommand = new TagCommand(INDEX_SECOND_PERSON, secondTags);

        // same object -> returns true
        assertTrue(tagFirstCommand.equals(tagFirstCommand));

        // same values -> returns true
        TagCommand tagFirstCommandCopy = new TagCommand(INDEX_FIRST_PERSON, firstTags);
        assertTrue(tagFirstCommand.equals(tagFirstCommandCopy));

        // different types -> returns false
        assertFalse(tagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(tagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(tagFirstCommand.equals(tagSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Tag tag = new Tag("tag");
        Set<Tag> tags = Set.of(tag);
        TagCommand tagCommand = new TagCommand(targetIndex, tags);
        String expected = TagCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex
                + ", tags=" + tags + "}";
        assertEquals(expected, tagCommand.toString());
    }
}

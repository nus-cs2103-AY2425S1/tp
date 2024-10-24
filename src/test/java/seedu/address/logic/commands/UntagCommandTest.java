package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
//import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code TagCommand}.
 */
public class UntagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    /*
    // this test causes errors in another test class when run with gradlew clean build.
    @Test
    public void execute_validIndexAndTags_success() throws CommandException {

        Person actualPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tag tag = new Tag("friends");
        Set<Tag> tags = Set.of(tag);
        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, tags);
        CommandResult result = untagCommand.execute(model);
        String expectedMessage = String.format(UntagCommand.MESSAGE_SUCCESS);

        Person expectedPerson = new Person(actualPerson.getName(), actualPerson.getStudentClass(),
                actualPerson.getPhone(), Set.of());

        assertEquals(expectedPerson, actualPerson);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // add back the tag
        actualPerson.addTags(tags);
    }
    */

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
    public void execute_tagNotExist_throwsCommandException() {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Tag tag = new Tag("friendss");
        Set<Tag> tags = Set.of(tag);
        UntagCommand untagCommand = new UntagCommand(INDEX_FIRST_PERSON, tags);

        assertCommandFailure(untagCommand, model, UntagCommand.MESSAGE_TAG_DOES_NOT_EXIST);
    }

    @Test
    public void equals() {
        Tag firstTag = new Tag("firstTag");
        Set<Tag> firstTags = Set.of(firstTag);
        Tag secondTag = new Tag("secondTag");
        Set<Tag> secondTags = Set.of(secondTag);
        UntagCommand untagFirstCommand = new UntagCommand(INDEX_FIRST_PERSON, firstTags);
        UntagCommand untagSecondCommand = new UntagCommand(INDEX_SECOND_PERSON, secondTags);

        // same object -> returns true
        assertTrue(untagFirstCommand.equals(untagFirstCommand));

        // same values -> returns true
        UntagCommand untagFirstCommandCopy = new UntagCommand(INDEX_FIRST_PERSON, firstTags);

        assertTrue(untagFirstCommand.equals(untagFirstCommandCopy));

        // different types -> returns false
        assertFalse(untagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(untagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(untagFirstCommand.equals(untagSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Tag tag = new Tag("tag");
        Set<Tag> tags = Set.of(tag);
        UntagCommand untagCommand = new UntagCommand(targetIndex, tags);
        String expected = UntagCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex
                + ", tags=" + tags + "}";
        assertEquals(expected, untagCommand.toString());
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_ALL_NONEXISTENT;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_SOME_NONEXISTENT;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_SUCCESS_FORCE;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_TAGS_IN_USE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalTags.BRIDES_SIDE;
import static seedu.address.testutil.TypicalTags.COLLEAGUES;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagList;
import seedu.address.testutil.TypicalTags;

/**
 * Contains tests for DeleteTagCommand.
 */
public class DeleteTagCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    /**
     * EP: Single existing tag.
     */
    @Test
    public void execute_existingTag_success() {
        // Create existing tag.
        Tag existingTag = BRIDES_SIDE;

        model.addTag(existingTag);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(List.of(existingTag), false);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        String expectedMessage = MESSAGE_SUCCESS;

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Multiple existing tags.
     */
    @Test
    public void execute_multipleExistingTags_success() {
        Tag existingFriendsTag = FRIENDS;
        Tag existingColleaguesTag = COLLEAGUES;
        List<Tag> existingTags = List.of(existingFriendsTag, existingColleaguesTag);
        model.addTags(existingTags);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(existingTags, false);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        String expectedMessage = MESSAGE_SUCCESS;

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Single non-existent tag.
     */
    @Test
    public void execute_nonExistentTag_error() {
        Tag nonExistentTag = BRIDES_SIDE;
        List<Tag> nonExistentTags = List.of(nonExistentTag);

        DeleteTagCommand newTagCommand = new DeleteTagCommand(nonExistentTags, false);
        String expectedMessage = MESSAGE_ALL_NONEXISTENT + nonExistentTags;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(newTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: A mix of existing and non-existent tags.
     */
    @Test
    public void execute_mixedExistingAndNonExistentTags_withError() {
        Tag existingTag = BRIDES_SIDE;
        Tag newColleaguesTag = COLLEAGUES;
        Tag newFriendsTag = FRIENDS;
        List<Tag> mixedTags = List.of(newFriendsTag, existingTag, newColleaguesTag);
        model.addTag(existingTag);

        DeleteTagCommand newTagCommand = new DeleteTagCommand(mixedTags, false);
        String expectedMessage = MESSAGE_SOME_NONEXISTENT + List.of(newFriendsTag, newColleaguesTag);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTags(mixedTags);

        // The list should still be updated, but the user will be notified of
        // non-existent tag(s).
        assertCommandSuccess(newTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Single existing tag with force delete.
     */
    @Test
    public void execute_existingTagForceDelete_success() {
        // Create existing tag.
        Tag existingTag = BRIDES_SIDE;

        model.addTag(existingTag);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(List.of(existingTag), true);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        String expectedMessage = MESSAGE_SUCCESS_FORCE;

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Attempt to delete tag in use throw error.
     */
    @Test
    public void execute_tagInUse_error() {
        // Create existing tag.
        Tag existingTag = BRIDES_SIDE;

        model.addTag(existingTag);
        model.addPerson(ALICE);
        model.setPerson(ALICE, new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getRsvpStatus(), Set.of(existingTag)));

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(List.of(existingTag), false);

        String expectedMessage = String.format(MESSAGE_TAGS_IN_USE, model.getTagsInUse());

        assertCommandFailure(deleteTagCommand, model, expectedMessage);
    }

    /**
     * EP: Attempt to delete tag in use with force delete.
     */
    @Test
    public void execute_tagInUseForceDelete_success() {
        // Arrange
        // Set up the existing tag and associate it with ALICE
        Tag existingTag = BRIDES_SIDE;
        Person aliceWithTag = new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getRsvpStatus(), Set.of(existingTag));

        model.addPerson(aliceWithTag);
        model.addTag(existingTag);

        // Create the force delete command for the existing tag
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(List.of(existingTag), true);

        // Set up the expected model after force delete
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(aliceWithTag, new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getRsvpStatus(), Set.of())); // ALICE with tag removed

        String expectedMessage = MESSAGE_SUCCESS_FORCE;

        // Act & Assert
        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_undoDeleteTagCommand_success() {
        Model originalModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Tag existingFriendsTag = FRIENDS;
        Tag existingColleaguesTag = COLLEAGUES;
        List<Tag> existingTags = List.of(existingFriendsTag, existingColleaguesTag);
        model.addTags(existingTags);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(existingTags, false);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        String expectedMessage = MESSAGE_SUCCESS;

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);

        model.updatePreviousCommand(deleteTagCommand);
        UndoCommand undoCommand = new UndoCommand();
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, originalModel);
    }

    @Test
    public void equals() {
        List<Tag> firstTags = new ArrayList<>();
        firstTags.add(BRIDES_SIDE);
        DeleteTagCommand firstDeleteTagCommand = new DeleteTagCommand(firstTags, false);

        List<Tag> secondTags = new ArrayList<>();
        secondTags.add(BRIDES_SIDE);
        DeleteTagCommand secondDeleteTagCommand = new DeleteTagCommand(secondTags, false);

        assertTrue(firstDeleteTagCommand.equals(secondDeleteTagCommand));

    }
}

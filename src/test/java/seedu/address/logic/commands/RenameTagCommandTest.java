package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BRIDES_SIDE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RenameTagCommand.MESSAGE_DUPLICATE;
import static seedu.address.logic.commands.RenameTagCommand.MESSAGE_NONEXISTENT;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

public class RenameTagCommandTest {
    private static final String NEW_NAME = VALID_TAG_FRIEND;
    private static final String TYPICAL_NAME = VALID_TAG_BRIDES_SIDE;
    private Model model = new ModelManager();

    /**
     * EP: Renaming an existing tag to an unused name (non-duplicate).
     */
    @Test
    public void execute_existingTagToNonExistentNewTag_success() {
        Tag existingTag = new Tag(TYPICAL_NAME);
        model.addTag(existingTag);
        RenameTagCommand renameTagCommand = new RenameTagCommand(existingTag, NEW_NAME);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(new Tag(NEW_NAME));

        String expectedMessage = RenameTagCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(renameTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Attempting to rename a tag that does not exist.
     */
    @Test
    public void execute_nonExistentTagToNonExistentNewTag_failure() {
        Tag newTag = new Tag(TYPICAL_NAME);
        RenameTagCommand renameTagCommand = new RenameTagCommand(newTag, NEW_NAME);
        String expectedMessage = MESSAGE_NONEXISTENT + newTag;
        assertCommandFailure(renameTagCommand, model, expectedMessage);
    }

    /**
     * EP: Attempting to rename a tag to a name that is already in use (duplicate).
     */
    @Test
    public void execute_existingTagToExistingNewTag_failure() {
        Tag existingTag = new Tag(NEW_NAME);
        model.addTag(existingTag);
        Tag newTag = new Tag(TYPICAL_NAME);
        model.addTag(newTag);
        RenameTagCommand renameTagCommand = new RenameTagCommand(newTag, NEW_NAME);
        String expectedMessage = MESSAGE_DUPLICATE + existingTag;
        assertCommandFailure(renameTagCommand, model, expectedMessage);
    }

    /**
     * EP: Attempting to rename a tag that does not exist
     * to a name that is already in use (duplicate).
     */
    @Test
    public void execute_nonExistentTagToExistingNewTag_failure() {
        Tag existingTag = new Tag(NEW_NAME);
        model.addTag(existingTag);
        Tag newTag = new Tag(TYPICAL_NAME);
        model.addTag(newTag);
        RenameTagCommand renameTagCommand = new RenameTagCommand(newTag, NEW_NAME);
        String expectedMessage = MESSAGE_DUPLICATE + existingTag;
        assertCommandFailure(renameTagCommand, model, expectedMessage);
    }

    @Test
    public void execute_undoRenameTagCommand_success() {
        Model originalModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Tag existingTag = new Tag(TYPICAL_NAME);
        model.addTag(existingTag);
        RenameTagCommand renameTagCommand = new RenameTagCommand(existingTag, NEW_NAME);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(new Tag(NEW_NAME));

        String expectedMessage = RenameTagCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(renameTagCommand, model, expectedMessage, expectedModel);

        model.updatePreviousCommand(renameTagCommand);
        UndoCommand undoCommand = new UndoCommand();
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, originalModel);
    }
}

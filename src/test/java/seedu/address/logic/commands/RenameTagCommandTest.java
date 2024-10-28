package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalTags;

public class RenameTagCommandTest {
    private Model model = new ModelManager();
    private static final String NEW_NAME = "NEW NAME";

    @Test
    public void execute_existingTag_success() {
        Tag existingTag = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        model.addTag(existingTag);
        RenameTagCommand renameTagCommand = new RenameTagCommand(existingTag, NEW_NAME);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(new Tag(NEW_NAME));

        String expectedMessage = RenameTagCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(renameTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentTag_failure() {
        Tag newTag = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        RenameTagCommand renameTagCommand = new RenameTagCommand(newTag, NEW_NAME);
        String expectedMessage = RenameTagCommand.MESSAGE_NONEXISTENT;
        assertCommandFailure(renameTagCommand, model, expectedMessage);
    }
}

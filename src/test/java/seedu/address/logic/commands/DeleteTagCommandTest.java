package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTags.BRIDES_SIDE;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains tests for DeleteTagCommand.
 */
public class DeleteTagCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_existingTag_success() {
        Tag existingTag = BRIDES_SIDE;
        List<Tag> existingTags = new ArrayList<Tag>();
        existingTags.add(existingTag);
        model.addTag(existingTag);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(existingTags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        String expectedMessage = DeleteTagCommand.MESSAGE_SUCCESS + "[" + existingTag + "]\n"
                + "Your tags: You have no tags.";

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentTag_failure() {
        Tag nonExistentTag = BRIDES_SIDE;
        List<Tag> nonExistentTags = new ArrayList<>();
        nonExistentTags.add(nonExistentTag);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(nonExistentTags);
        String expectedMessage = DeleteTagCommand.MESSAGE_NONEXISTENT;

        assertCommandFailure(deleteTagCommand, model, expectedMessage);
    }
}

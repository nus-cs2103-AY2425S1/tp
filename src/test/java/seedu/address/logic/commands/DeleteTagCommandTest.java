package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTags.BRIDES_SIDE;
import static seedu.address.testutil.TypicalTags.COLLEAGUES;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalTags;

/**
 * Contains tests for DeleteTagCommand.
 */
public class DeleteTagCommandTest {

    private Model model = new ModelManager();

    /**
     * EP: Single existing tag.
     */
    @Test
    public void execute_existingTag_success() {
        Tag existingTag = BRIDES_SIDE;
        List<Tag> existingTags = List.of(existingTag);
        model.addTag(existingTag);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(existingTags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        String expectedMessage = DeleteTagCommand.MESSAGE_SUCCESS;

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

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(existingTags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        String expectedMessage = DeleteTagCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Single non-existent tag.
     */
    @Test
    public void execute_nonExistentTag_successWithWarning() {
        Tag nonExistentTag = BRIDES_SIDE;
        List<Tag> nonExistentTags = List.of(nonExistentTag);

        DeleteTagCommand newTagCommand = new DeleteTagCommand(nonExistentTags);
        String expectedMessage = DeleteTagCommand.MESSAGE_NONEXISTENT;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTags(nonExistentTags);

        // No exception is thrown, but the user will be notified of
        // non-existent tag(s).
        assertCommandSuccess(newTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: A mix of existing and non-existent tags.
     */
    @Test
    public void execute_mixedExistingAndNonExistentTags_successWithWarning() {
        Tag existingTag = TypicalTags.BRIDES_SIDE;
        Tag newColleaguesTag = TypicalTags.COLLEAGUES;
        Tag newFriendsTag = TypicalTags.FRIENDS;
        List<Tag> mixedTags = List.of(newFriendsTag, existingTag, newColleaguesTag);
        model.addTag(existingTag);

        DeleteTagCommand newTagCommand = new DeleteTagCommand(mixedTags);
        String expectedMessage = DeleteTagCommand.MESSAGE_NONEXISTENT;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTags(mixedTags);

        // The list should still be updated, but the user will be notified of
        // non-existent tag(s).
        assertCommandSuccess(newTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        List<Tag> firstTags = new ArrayList<>();
        firstTags.add(BRIDES_SIDE);
        DeleteTagCommand firstDeleteTagCommand = new DeleteTagCommand(firstTags);

        List<Tag> secondTags = new ArrayList<>();
        secondTags.add(BRIDES_SIDE);
        DeleteTagCommand secondDeleteTagCommand = new DeleteTagCommand(secondTags);

        assertTrue(firstDeleteTagCommand.equals(secondDeleteTagCommand));

    }
}

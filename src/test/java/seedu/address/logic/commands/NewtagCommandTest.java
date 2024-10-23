package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagList;
import seedu.address.testutil.TypicalTags;

/**
 * Contains tests for NewtagCommand.
 */
public class NewtagCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_newTag_success() {
        Tag newTag = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        List<Tag> newTags = new ArrayList<>();
        newTags.add(newTag);
        NewtagCommand newTagCommand = new NewtagCommand(newTags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(newTag);

        String expectedMessage = NewtagCommand.MESSAGE_SUCCESS + newTags + "\n"
                + "Your tags: " + expectedModel.getTagList();

        assertCommandSuccess(newTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleNewTags_success() {
        Tag tagBridesFriend = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        Tag tagColleagues = TypicalTags.COLLEAGUES;
        List<Tag> newTags = new ArrayList<>();
        newTags.add(tagBridesFriend);
        newTags.add(tagColleagues);
        NewtagCommand newTagCommand = new NewtagCommand(newTags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(tagBridesFriend);
        expectedModel.addTag(tagColleagues);

        String expectedMessage = NewtagCommand.MESSAGE_SUCCESS + newTags + "\n"

                + "Your tags: " + expectedModel.getTagList();

        assertCommandSuccess(newTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTag_failure() {
        Tag duplicateTag = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        List<Tag> duplicateTags = new ArrayList<>();
        duplicateTags.add(duplicateTag);
        model.addTags(duplicateTags);

        NewtagCommand newTagCommand = new NewtagCommand(duplicateTags);
        String expectedMessage = NewtagCommand.MESSAGE_DUPLICATE;

        assertCommandFailure(newTagCommand, model, expectedMessage);
    }

    @Test
    public void execute_tooManyTags_failure() {
        Tag newTag = TypicalTags.VALID_TAG_BRIDES_FRIEND;
        List<Tag> newTags = new ArrayList<>();
        newTags.add(newTag);

        for (int i = 0; i < TagList.MAXIMUM_TAGLIST_SIZE; i++) {
            model.addTag(new Tag(String.valueOf(i)));
        }

        NewtagCommand newTagCommand = new NewtagCommand(newTags);
        String expectedMessage = NewtagCommand.MESSAGE_TOO_MANY_TAGS;

        assertCommandFailure(newTagCommand, model, expectedMessage);
    }
}

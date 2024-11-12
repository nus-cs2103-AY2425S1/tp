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
public class NewTagCommandTest {

    private Model model = new ModelManager();

    /**
     * EP: Valid tag name(s) that do(es) not already exist(s).
     */
    @Test
    public void execute_newTag_success() {
        Tag newTag = TypicalTags.BRIDES_SIDE;
        List<Tag> newTags = new ArrayList<>();
        newTags.add(newTag);
        NewTagCommand newTagCommand = new NewTagCommand(newTags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(newTag);

        String expectedMessage = NewTagCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(newTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleNewTags_success() {
        Tag tagBridesFriend = TypicalTags.BRIDES_SIDE;
        Tag tagColleagues = TypicalTags.COLLEAGUES;
        List<Tag> newTags = List.of(tagBridesFriend, tagColleagues);
        NewTagCommand newTagCommand = new NewTagCommand(newTags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(tagBridesFriend);
        expectedModel.addTag(tagColleagues);

        String expectedMessage = NewTagCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(newTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Valid tag name(s) that already exist (ie. duplicate).
     */
    @Test
    public void execute_duplicateTag_successWithWarning() {
        Tag duplicateTag = TypicalTags.BRIDES_SIDE;
        List<Tag> duplicateTags = new ArrayList<>();
        duplicateTags.add(duplicateTag);
        model.addTags(duplicateTags);

        NewTagCommand newTagCommand = new NewTagCommand(duplicateTags);
        String expectedMessage = NewTagCommand.MESSAGE_ALL_DUPLICATE + duplicateTags;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTags(duplicateTags);

        // No exception thrown, but user will be informed of duplicate(s).
        assertCommandSuccess(newTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_mixedNewAndDuplicateTags_successWithWarning() {
        Tag duplicateTag = TypicalTags.BRIDES_SIDE;
        Tag newColleaguesTag = TypicalTags.COLLEAGUES;
        Tag newFriendsTag = TypicalTags.FRIENDS;
        List<Tag> mixedTags = List.of(newFriendsTag, duplicateTag, newColleaguesTag);
        model.addTag(duplicateTag);

        NewTagCommand newTagCommand = new NewTagCommand(mixedTags);
        String expectedMessage = NewTagCommand.MESSAGE_SOME_DUPLICATE + List.of(duplicateTag);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTags(mixedTags);

        // The list should still be updated, but the user will be notified of duplicate(s).
        assertCommandSuccess(newTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Tags that almost exceed the tag list size limit.
     */
    @Test
    public void execute_maximumNumberOfTags_success() {
        Tag newTag = TypicalTags.BRIDES_SIDE;
        List<Tag> newTags = new ArrayList<>();
        newTags.add(newTag);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Just below boundary value
        for (int i = 0; i < TagList.MAXIMUM_TAGLIST_SIZE - 1; i++) {
            model.addTag(new Tag(String.valueOf(i)));
            expectedModel.addTag(new Tag(String.valueOf(i)));
        }

        NewTagCommand newTagCommand = new NewTagCommand(newTags);
        expectedModel.addTag(newTag);
        String expectedMessage = NewTagCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(newTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * EP: Tags that exceed the tag list size limit.
     */
    @Test
    public void execute_tooManyTags_failure() {
        Tag newTag = TypicalTags.BRIDES_SIDE;
        List<Tag> newTags = new ArrayList<>();
        newTags.add(newTag);

        // Boundary value
        for (int i = 0; i < TagList.MAXIMUM_TAGLIST_SIZE; i++) {
            model.addTag(new Tag(String.valueOf(i)));
        }

        NewTagCommand newTagCommand = new NewTagCommand(newTags);
        String expectedMessage = NewTagCommand.MESSAGE_TOO_MANY_TAGS;

        assertCommandFailure(newTagCommand, model, expectedMessage);
    }

    @Test
    public void execute_undoNewTag_success() {
        Model originalModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Tag newTag = TypicalTags.BRIDES_SIDE;
        List<Tag> newTags = new ArrayList<>();
        newTags.add(newTag);
        NewTagCommand newTagCommand = new NewTagCommand(newTags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(newTag);

        String expectedMessage = NewTagCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(newTagCommand, model, expectedMessage, expectedModel);

        model.updatePreviousCommand(newTagCommand);
        UndoCommand undoCommand = new UndoCommand();
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, originalModel);
    }
}

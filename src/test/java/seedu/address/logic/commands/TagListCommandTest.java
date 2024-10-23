package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains tests for TagListCommand.
 */
public class TagListCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_tagListCommandWithTags_success() {
        model.addTag(FRIENDS);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(FRIENDS);
        TagListCommand tagListCommand = new TagListCommand();
        String expectedMessage = TagListCommand.MESSAGE_SUCCESS + model.getTagList();
        assertCommandSuccess(tagListCommand, model, expectedMessage, expectedModel);
    }
}

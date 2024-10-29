package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalTags.BRIDES_SIDE;
import static seedu.address.testutil.TypicalTags.GROOMS_SIDE;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains tests for DeleteTagCommand.
 */
public class DeleteTagCommandTest {

    private Model model;
    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        // Add initial tags and persons for testing
        model.addTag(BRIDES_SIDE);
        model.addTag(GROOMS_SIDE);
        model.addPerson(ALICE); // Add Person with BRIDES_SIDE tag
        model.addPerson(BOB); // Add Person with GROOMS_SIDE tag
    }


    @Test
    public void execute_existingTag_success() {
        Tag existingTag = BRIDES_SIDE;
        List<Tag> existingTags = new ArrayList<Tag>();
        existingTags.add(existingTag);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(existingTags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        String expectedMessage = DeleteTagCommand.MESSAGE_SUCCESS + "[" + existingTag + "]\n"
                + "Your tags: groom's side";

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

    @Test
    public void executeDeleteMultipleTagsSuccess() {
        List<Tag> tagsToDelete = new ArrayList<>();
        tagsToDelete.add(BRIDES_SIDE);
        tagsToDelete.add(GROOMS_SIDE);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(tagsToDelete);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(BRIDES_SIDE);
        expectedModel.deleteTag(GROOMS_SIDE);

        String expectedMessage = DeleteTagCommand.MESSAGE_SUCCESS + "[" + BRIDES_SIDE + ", " + GROOMS_SIDE + "]\n"
                + "Your tags: " + expectedModel.getTagList();

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }


}

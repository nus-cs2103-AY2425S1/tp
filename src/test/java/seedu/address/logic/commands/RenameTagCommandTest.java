package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class RenameTagCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }


    @Test
    public void execute_renameTag_success() {
        String newTag = "friends1";
        RenameTagCommand renameTagCommand = new RenameTagCommand("friends", newTag);
        String expectedMessage = String.format(RenameTagCommand.MESSAGE_RENAME_TAG_SUCCESS, newTag);
        assertCommandSuccess(renameTagCommand, model, expectedMessage, expectedModel);

        String newTag2 = "friends";
        RenameTagCommand renameTagCommand2 = new RenameTagCommand("friends1", newTag2);
        String expectedMessage2 = String.format(RenameTagCommand.MESSAGE_RENAME_TAG_SUCCESS, newTag2);
        assertCommandSuccess(renameTagCommand2, model, expectedMessage2, expectedModel);
    }

    @Test
    public void execute_renameInvalidTag_failure() {
        String tag = "iDontExist";
        String newTag = "besties";

        RenameTagCommand renameTagCommand = new RenameTagCommand(tag, newTag);

        String expectedMessage = String.format(RenameTagCommand.MESSAGE_TAG_NOT_FOUND, tag);

        assertCommandFailure(renameTagCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().withTags("friends").build();
        String tagName = person.getTags().iterator().next().getTagName();
        final RenameTagCommand standardCommand = new RenameTagCommand(tagName, "frenemies");

        // same values -> returns true
        RenameTagCommand commandWithSameValues = new RenameTagCommand(tagName, "frenemies");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different new tag -> returns false
        assertFalse(standardCommand.equals(new RenameTagCommand(tagName, "frenemiess")));

        // different old tag -> returns false
        assertFalse(standardCommand.equals(new RenameTagCommand("biends", "frenemies")));
    }
}

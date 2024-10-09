package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.OTHER_VALID_GROUP_NAME;
import static seedu.address.logic.commands.CommandTestUtil.OTHER_VALID_STUDENTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.GroupCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class GroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandFailure(new GroupCommand(VALID_GROUP_NAME, VALID_STUDENTS), model,
                String.format(MESSAGE_ARGUMENTS, VALID_GROUP_NAME, VALID_STUDENTS));
    }

    @Test
    public void equals() {
        final GroupCommand standardCommand = new GroupCommand(VALID_GROUP_NAME, VALID_STUDENTS);

        // same values -> returns true
        GroupCommand commandWithSameValues = new GroupCommand(VALID_GROUP_NAME, VALID_STUDENTS);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different groupname -> returns false
        assertFalse(standardCommand.equals(new GroupCommand(OTHER_VALID_GROUP_NAME, VALID_STUDENTS)));

        // different students -> returns false
        assertFalse(standardCommand.equals(new GroupCommand(VALID_GROUP_NAME, OTHER_VALID_STUDENTS)));
    }
}

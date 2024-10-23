package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ModCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ModuleName;

public class ModCommandTest {

    private final Model model = new ModelManager();
    @Test
    public void execute() {
        final ModuleName moduleName = new ModuleName(VALID_MODNAME_AMY);

        assertCommandFailure(new ModCommand(INDEX_FIRST_PERSON, moduleName), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), VALID_MODNAME_AMY));
    }

    @Test
    public void equals() {
        final ModCommand standardCommand = new ModCommand(INDEX_FIRST_PERSON,
                new ModuleName(VALID_MODNAME_AMY));

        // same values -> returns true
        ModCommand commandWithSameValues = new ModCommand(INDEX_FIRST_PERSON,
                new ModuleName(VALID_MODNAME_AMY));
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new ModCommand(INDEX_SECOND_PERSON,
                new ModuleName(VALID_MODNAME_AMY)));

        // different remark -> returns false
        assertNotEquals(standardCommand, new ModCommand(INDEX_FIRST_PERSON,
                new ModuleName(VALID_MODNAME_BOB)));
    }



}

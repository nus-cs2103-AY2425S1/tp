package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ListExpiringPoliciesCommandTest {

    private final ListExpiringPoliciesCommand listExpiringPoliciesCommand = new ListExpiringPoliciesCommand();
    private final Model model = new ModelManager();

    @Test
    public void execute_listExpiringPolicies_throwsCommandException() {
        /* this test matches the current stage of development when the logic has yet to be implemented and a
         placeholder exception is used*/
        CommandException thrown = assertThrows(CommandException.class, () -> listExpiringPoliciesCommand.execute(model));
        assertEquals("listExpiringPolicies command has not been implemented yet", thrown.getMessage());
    }

    @Test
    public void equals() {
        // since ListExpiringPoliciesCommand has no state, all instances should be equal
        ListExpiringPoliciesCommand firstCommand = new ListExpiringPoliciesCommand();
        ListExpiringPoliciesCommand secondCommand = new ListExpiringPoliciesCommand();

        // same object -> returns true
        assertEquals(firstCommand, firstCommand);

        // different objects, same class -> returns true
        assertEquals(firstCommand, secondCommand);

        // Null -> returns false
        assertNotEquals(null, firstCommand);

        // Different types -> returns false
        assertNotEquals(firstCommand, "Some string");

    }
}

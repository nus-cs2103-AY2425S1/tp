package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;



public class ViewStatusCommandTest {
    public final Name amy = new Name(VALID_NAME_AMY);
    public final Name bob = new Name(VALID_NAME_BOB);
    public final Job amyJob = new Job(VALID_JOB_AMY);
    public final Job bobJob = new Job(VALID_JOB_BOB);

    @Test
    public void constructor_validInput_success() {
        Name name = new Name("John Doe");
        Job job = new Job("Software Engineer");

        ViewStatusCommand command = new ViewStatusCommand(name, job);

        // Verify that the name and job are correctly initialized
        assertEquals(name, command.name);
        assertEquals(job, command.job);
    }

    @Test
    public void execute_commandNotImplementedYet_throwsCommandException() {
        Model model = new ModelManager();
        Name name = new Name("John Doe");
        Job job = new Job("Software Engineer");

        ViewStatusCommand command = new ViewStatusCommand(name, job);

        // Expect the CommandException with a specific message
        assertThrows(CommandException.class, () -> command.execute(model),
                "View command not implemented yet");
    }

    @Test
    public void equals() {
        final ViewStatusCommand standardCommand = new ViewStatusCommand(amy, amyJob);

        // same values -> returns true
        ViewStatusCommand commandWithSameValues = new ViewStatusCommand(amy, amyJob);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ViewStatusCommand(bob, amyJob)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new ViewStatusCommand(amy, bobJob)));
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Name;



public class ViewStatusCommandTest {

    @Test
    public void constructor_validInput_success() {
        Name name = new Name("John Doe");
        String job = "Software Engineer";

        ViewStatusCommand command = new ViewStatusCommand(name, job);

        // Verify that the name and job are correctly initialized
        assertEquals(name, command.name);
        assertEquals(job, command.job);
    }

    @Test
    public void execute_commandNotImplementedYet_throwsCommandException() {
        Model model = new ModelManager();
        Name name = new Name("John Doe");
        String job = "Software Engineer";

        ViewStatusCommand command = new ViewStatusCommand(name, job);

        // Expect the CommandException with a specific message
        assertThrows(CommandException.class, () -> command.execute(model),
                "Remark command not implemented yet");
    }
}

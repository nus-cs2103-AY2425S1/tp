package seedu.address.logic.commands.searchmode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;


public class ExitSearchModeCommandTest {
    private ModelManager model = new ModelManager();

    @Test
    public void execute() {
        ExitSearchModeCommand command = new ExitSearchModeCommand();
        model.setSearchMode(true);
        command.execute(model, null);
        assertEquals(model.getSearchMode(), false);


    }

    @Test
    public void equals() {
        ExitSearchModeCommand command = new ExitSearchModeCommand();
        assertEquals(command, new ExitSearchModeCommand());
    }
}

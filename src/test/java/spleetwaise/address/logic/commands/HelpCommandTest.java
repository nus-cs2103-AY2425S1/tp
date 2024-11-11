package spleetwaise.address.logic.commands;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.commons.logic.commands.CommandResult;

public class HelpCommandTest {
    private AddressBookModel model = new AddressBookModelManager();
    private AddressBookModel expectedModel = new AddressBookModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommand.SHOWING_HELP_MESSAGE, true, false);
        CommandTestUtil.assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}

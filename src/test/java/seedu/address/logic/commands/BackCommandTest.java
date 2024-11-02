package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class BackCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_inInspectWindow_returnsToMainWindow() {
        AddressBookParser.setInspect(true);
        assertCommandSuccess(new BackCommand(), model, BackCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_inMainWindow_staysMainWindow() {
        AddressBookParser.setInspect(false);
        assertCommandSuccess(new BackCommand(), model, BackCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

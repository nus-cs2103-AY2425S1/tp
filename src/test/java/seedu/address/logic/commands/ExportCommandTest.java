package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGoods.getTypicalGoodsReceipts;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;



public class ExportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalGoodsReceipts());

    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalGoodsReceipts());

    @Test
    public void execute_export_success() {
        Command exportCommand = new ExportCommand();
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
    }
}

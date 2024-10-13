package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class IncomeCommandTest {
    @Test
    public void emptyAddressBook_noPaidAmount_noOwedAmount() {
        Model model = new ModelManager();
        CommandResult commandResult = new IncomeCommand().execute(model);
        assertEquals(commandResult.getFeedbackToUser(), "Total Paid: 0.0   Total Owed: 0.0");
    }

//    @Test
//    public void addPerson_Updated
}

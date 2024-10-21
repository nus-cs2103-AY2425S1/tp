package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;

import org.junit.jupiter.api.Test;

import seedu.address.model.BuyerList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyBuyerList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyBuyerList_success() {
        Model model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList());
        Model expectedModel = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList());
        expectedModel.setBuyerList(new BuyerList());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

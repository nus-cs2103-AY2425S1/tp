package seedu.address.logic.commands.meetup;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

import org.junit.jupiter.api.Test;

import seedu.address.model.MeetUpList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyMeetUpList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyMeetUpList_success() {
        Model model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList(),
                getTypicalPropertyList());
        Model expectedModel = new ModelManager(getTypicalBuyerList(), new UserPrefs(),
                getTypicalMeetUpList(), getTypicalPropertyList());
        expectedModel.setMeetUpList(new MeetUpList());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

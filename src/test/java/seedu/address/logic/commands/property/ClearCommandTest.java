package seedu.address.logic.commands.property;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyList;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyPropertyList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyPropertyList_success() {
        Model model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList(),
                getTypicalPropertyList());
        Model expectedModel = new ModelManager(getTypicalBuyerList(), new UserPrefs(),
                getTypicalMeetUpList(), getTypicalPropertyList());
        expectedModel.setPropertyList(new PropertyList());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

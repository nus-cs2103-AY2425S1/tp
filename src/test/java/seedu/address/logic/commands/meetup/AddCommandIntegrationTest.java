package seedu.address.logic.commands.meetup;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyList;
import seedu.address.model.UserPrefs;
import seedu.address.model.meetup.MeetUp;
import seedu.address.testutil.meetup.MeetUpBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList(),
                getTypicalPropertyList());
    }

    @Test
    public void execute_newMeetUp_success() {
        MeetUp meetUp = new MeetUpBuilder().build();

        Model expectedModel = new ModelManager(model.getBuyerList(), new UserPrefs(), getTypicalMeetUpList(),
                new PropertyList(model.getPropertyList()));
        expectedModel.addMeetUp(meetUp);
        CommandResult expectedCommandResult = new CommandResult(String.format(
                AddCommand.MESSAGE_SUCCESS, Messages.format(meetUp)), false, false, true, false, false);

        assertCommandSuccess(new AddCommand(meetUp), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateMeetUp_throwsCommandException() {
        MeetUp meetUpInList = model.getMeetUpList().getMeetUpList().get(0);
        assertCommandFailure(new AddCommand(meetUpInList), model,
                AddCommand.MESSAGE_DUPLICATE_MEETUP);
    }

}

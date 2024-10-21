package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meetup.MeetUp;
import seedu.address.testutil.MeetUpBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddMeetUpCommand}.
 */
public class AddMeetUpCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalMeetUpList());
    }

    @Test
    public void execute_newMeetUp_success() {
        MeetUp meetUp = new MeetUpBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), getTypicalMeetUpList());
        expectedModel.addMeetUp(meetUp);

        assertCommandSuccess(new AddMeetUpCommand(meetUp), model,
                String.format(AddMeetUpCommand.MESSAGE_SUCCESS, Messages.format(meetUp)),
                expectedModel);
    }

    @Test
    public void execute_duplicateMeetUp_throwsCommandException() {
        MeetUp meetUpInList = model.getMeetUpList().getMeetUpList().get(0);
        assertCommandFailure(new AddMeetUpCommand(meetUpInList), model,
                AddMeetUpCommand.MESSAGE_DUPLICATE_MEETUP);
    }

}

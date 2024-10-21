// package seedu.address.logic.commands.meetup;

// import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
// import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import seedu.address.logic.commands.CommandResult;
// import seedu.address.model.Model;
// import seedu.address.model.ModelManager;
// import seedu.address.model.UserPrefs;

// public class ViewCommandTest {
//     private Model model;
//     private Model expectedModel;

//     @BeforeEach
//     public void setUp() {
//         model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList());
//         expectedModel = new ModelManager(model.getBuyerList(), new UserPrefs(), model.getMeetUpList());
//     }

//     @Test
//     public void execute_viewMeetUp_success() {
//         CommandResult expectedCommandResult = new CommandResult(ViewCommand.MESSAGE_SUCCESS,
//                 false, false, true, false);
//         assertCommandSuccess(new ViewCommand(), model, expectedCommandResult, expectedModel);
//     }
// }

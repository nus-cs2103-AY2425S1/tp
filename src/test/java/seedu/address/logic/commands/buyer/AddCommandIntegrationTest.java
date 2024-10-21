// package seedu.address.logic.commands.buyer;

// import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
// import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
// import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import seedu.address.logic.Messages;
// import seedu.address.model.Model;
// import seedu.address.model.ModelManager;
// import seedu.address.model.UserPrefs;
// import seedu.address.model.buyer.Buyer;
// import seedu.address.testutil.buyer.BuyerBuilder;

// /**
//  * Contains integration tests (interaction with the Model) for {@code AddCommand}.
//  */
// public class AddCommandIntegrationTest {

//     private Model model;

//     @BeforeEach
//     public void setUp() {
//         model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList());
//     }

//     @Test
//     public void execute_newBuyer_success() {
//         Buyer validBuyer = new BuyerBuilder().build();

//         Model expectedModel = new ModelManager(model.getBuyerList(), new UserPrefs(), getTypicalMeetUpList());
//         expectedModel.addBuyer(validBuyer);

//         assertCommandSuccess(new AddCommand(validBuyer), model,
//                 String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validBuyer)),
//                 expectedModel);
//     }

//     @Test
//     public void execute_duplicateBuyer_throwsCommandException() {
//         Buyer buyerInList = model.getBuyerList().getBuyerList().get(0);
//         assertCommandFailure(new AddCommand(buyerInList), model,
//                 AddCommand.MESSAGE_DUPLICATE_BUYER);
//     }

// }

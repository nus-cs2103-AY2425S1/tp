package seedu.address.logic.commands;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

//import javafx.application.Platform;
import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;



class ViewCommandTest {
    public static final Index VIEW_STUB = Index.fromOneBased(1);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    //    @BeforeAll
    //    static void initToolkit() {
    //        if (!Platform.isFxApplicationThread()) {
    //            Platform.startup(() -> {}); // Initialize the JavaFX toolkit
    //        }
    //    }

    @Test
    void testEquals() {
        ViewCommand viewCommand = new ViewCommand(VIEW_STUB);
        //same object -> true

        assertTrue(viewCommand.equals(new ViewCommand(VIEW_STUB)));
        assertTrue(viewCommand.equals(viewCommand));
        // null -> return false
        assertFalse(viewCommand.equals(null));
        // different command type -> return false
        assertFalse(viewCommand.equals(new ClearCommand()));
        assertFalse(viewCommand.equals(new ViewCommand(Index.fromOneBased(2))));

    }


    //    @Test
    //    public void execute_validIndex_personDetailsDisplayed() throws Exception {
    //        // Set up CountDownLatch to wait for JavaFX thread
    //        CountDownLatch latch = new CountDownLatch(1);
    //
    //        Index validIndex = Index.fromZeroBased(0);
    //
    //        ViewCommand viewCommand = new ViewCommand(validIndex);
    //
    //        // Use Platform.runLater to execute UI-related code on JavaFX Application Thread
    //        Platform.runLater(() -> {
    //            try {
    //                // Execute the command and assert the expected outcome
    //                CommandResult result = viewCommand.execute(model);
    //                assertEquals("Person details displayed.", result.getFeedbackToUser());
    //
    //            } catch (CommandException e) {
    //                fail("Command execution failed.");
    //            } finally {
    //                latch.countDown();
    //            }
    //        });
    //        latch.await();
    //    }
    //
    //    @Test
    //    public void execute_invalidIndex_throwsCommandException() throws Exception {
    //        CountDownLatch latch = new CountDownLatch(1);
    //
    //        Platform.runLater(() -> {
    //            try {
    //                // Setup an invalid index
    //                Index invalidIndex = Index.fromZeroBased(100);
    //                ViewCommand viewCommand = new ViewCommand(invalidIndex);
    //
    //                // Expect CommandException
    //                assertThrows(CommandException.class, () -> viewCommand.execute(model));
    //
    //            } finally {
    //                latch.countDown();
    //            }
    //        });
    //
    //        latch.await();
    //    }
}

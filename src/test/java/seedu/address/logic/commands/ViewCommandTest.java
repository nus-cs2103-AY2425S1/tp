package seedu.address.logic.commands;

import com.sun.glass.ui.View;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Remark;

import seedu.address.logic.commands.exceptions.CommandException;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

class ViewCommandTest {
    public static final Index VIEW_STUB = Index.fromOneBased(1);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {});  // Initialize the JavaFX toolkit
    }

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


    @Test
    public void execute_validIndex_personDetailsDisplayed() throws Exception {
        // Set up CountDownLatch to wait for JavaFX thread
        CountDownLatch latch = new CountDownLatch(1);

        // Prepare your model and other objects here
        Index validIndex = Index.fromZeroBased(0);

        // Create a ViewCommand
        ViewCommand viewCommand = new ViewCommand(validIndex);

        // Use Platform.runLater to execute UI-related code on JavaFX Application Thread
        Platform.runLater(() -> {
            try {
                // Execute the command and assert the expected outcome
                CommandResult result = viewCommand.execute(model);
                assertEquals("Person details displayed.", result.getFeedbackToUser());

            } catch (CommandException e) {
                fail("Command execution failed.");
            } finally {
                latch.countDown();  // Signal completion of the task
            }
        });

        // Wait for the latch to reach 0, ensuring the test doesn't exit before UI task is done
        latch.await();
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                // Setup an invalid index
                Index invalidIndex = Index.fromZeroBased(100);
                ViewCommand viewCommand = new ViewCommand(invalidIndex);

                // Expect CommandException
                assertThrows(CommandException.class, () -> viewCommand.execute(model));

            } finally {
                latch.countDown();  // Signal completion of the task
            }
        });

        latch.await();  // Wait for the task to complete
    }
}

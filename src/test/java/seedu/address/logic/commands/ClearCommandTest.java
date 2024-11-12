package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.MainWindow;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        try (MockedStatic<MainWindow> mockedStatic = mockStatic(MainWindow.class)) {
            mockedStatic.when(() -> MainWindow.showConfirmationDialog(ClearCommand.MESSAGE_CONFIRMATION))
                    .thenReturn(true);

            assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
        }
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        try (MockedStatic<MainWindow> mockedStatic = mockStatic(MainWindow.class)) {
            mockedStatic.when(() -> MainWindow.showConfirmationDialog(ClearCommand.MESSAGE_CONFIRMATION))
                    .thenReturn(true);

            assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
        }
    }

    @Test
    public void execute_clearCommand_aborted() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        try (MockedStatic<MainWindow> mockedStatic = mockStatic(MainWindow.class)) {
            mockedStatic.when(() -> MainWindow.showConfirmationDialog(ClearCommand.MESSAGE_CONFIRMATION))
                    .thenReturn(false);

            ClearCommand clearCommand = new ClearCommand();
            CommandResult result = clearCommand.execute(model);

            assertEquals(ClearCommand.MESSAGE_ABORT, result.getFeedbackToUser());
        }
    }

}

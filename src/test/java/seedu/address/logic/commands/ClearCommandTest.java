package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.AlertFactory;

public class ClearCommandTest {

    @Test
    public void execute_clearConfirmed_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        // Mock the Alert and AlertFactory
        Alert mockAlert = Mockito.mock(Alert.class);
        Mockito.when(mockAlert.showAndWait()).thenReturn(Optional.of(ButtonType.OK));

        AlertFactory mockAlertFactory = Mockito.mock(AlertFactory.class);
        Mockito.when(mockAlertFactory.createAlert(Alert.AlertType.CONFIRMATION))
                .thenReturn(mockAlert);

        ClearCommand clearCommand = new ClearCommand(mockAlertFactory);

        CommandResult result = clearCommand.execute(model);

        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel.getAddressBook(), model.getAddressBook());
    }

    @Test
    public void execute_clearCancelled_unchanged() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Mock the Alert and AlertFactory
        Alert mockAlert = Mockito.mock(Alert.class);
        Mockito.when(mockAlert.showAndWait()).thenReturn(Optional.of(ButtonType.CANCEL));

        AlertFactory mockAlertFactory = Mockito.mock(AlertFactory.class);
        Mockito.when(mockAlertFactory.createAlert(Alert.AlertType.CONFIRMATION))
                .thenReturn(mockAlert);

        ClearCommand clearCommand = new ClearCommand(mockAlertFactory);

        CommandResult result = clearCommand.execute(model);

        assertEquals(ClearCommand.CANCEL_CLEAR, result.getFeedbackToUser());
        assertEquals(expectedModel.getAddressBook(), model.getAddressBook());
    }

    @Test
    public void execute_emptyAddressBookClearConfirmed_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        // Mock the Alert and AlertFactory
        Alert mockAlert = Mockito.mock(Alert.class);
        Mockito.when(mockAlert.showAndWait()).thenReturn(Optional.of(ButtonType.OK));

        AlertFactory mockAlertFactory = Mockito.mock(AlertFactory.class);
        Mockito.when(mockAlertFactory.createAlert(Alert.AlertType.CONFIRMATION))
                .thenReturn(mockAlert);

        ClearCommand clearCommand = new ClearCommand(mockAlertFactory);

        CommandResult result = clearCommand.execute(model);

        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel.getAddressBook(), model.getAddressBook());
    }
}

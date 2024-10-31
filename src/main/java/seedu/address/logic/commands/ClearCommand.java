package seedu.address.logic.commands;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import seedu.address.model.Model;
import seedu.address.ui.AlertFactory;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String CANCEL_CLEAR = "Clear operation cancelled.";

    private final AlertFactory alertFactory;

    /**
     * Creates a ClearCommand with a default AlertFactory.
     */
    public ClearCommand() {
        this(new AlertFactory());
    }

    /**
     * Creates a ClearCommand with the specified AlertFactory.
     * This constructor is used for testing purposes.
     *
     * @param alertFactory The factory to create alert dialogs.
     */
    public ClearCommand(AlertFactory alertFactory) {
        this.alertFactory = alertFactory;
    }

    @Override
    public CommandResult execute(Model model) {
        Alert alert = alertFactory.createAlert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Clear");
        alert.setHeaderText("Are you sure you want to clear the address book?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            model.setAddressBook(new seedu.address.model.AddressBook());
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(CANCEL_CLEAR);
        }
    }
}

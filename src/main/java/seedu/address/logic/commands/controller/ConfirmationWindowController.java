package seedu.address.logic.commands.controller;

import seedu.address.ui.ConfirmationWindow;

/**
 * Controller to show Confirmation Window.
 */
public class ConfirmationWindowController implements ConfirmationController {

    @Override
    public boolean isConfirmed(String title, String contentText) {
        ConfirmationWindow confirmationWindow = new ConfirmationWindow(title, contentText);
        confirmationWindow.show();
        return confirmationWindow.isConfirmed();
    }
}

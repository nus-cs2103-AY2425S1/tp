package seedu.address.logic.commands.controller;

/**
 * Controller that by pass confirmation window for testing purposes.
 */
public class ConfirmationBypassController implements ConfirmationController {
    private boolean confirmationResult = true;
    @Override
    public boolean isConfirmed(String title, String contentText) {
        return confirmationResult;
    }

    /**
     * Sets the confirmation result to be returned by isConfirmed.
     *
     * @param confirmationResult The value to return from isConfirmed.
     */
    public void setConfirmationResult(boolean confirmationResult) {
        this.confirmationResult = confirmationResult;
    }

}
